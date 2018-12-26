package mocha.client;

import com.google.common.collect.Queues;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.GameLogic;
import mocha.game.LocalPlayer;
import mocha.game.LoginSuccessPacket;
import mocha.game.PlayerIdentityPacket;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkUpdatePacket;
import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.game.world.chunk.tile.TileType;
import mocha.game.world.chunk.tile.TileUpdatePacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.EntityType;
import mocha.game.world.entity.EntityUpdatePacket;
import mocha.game.world.entity.ItemEntity;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.prototype.EntityPrototypeUpdatePacket;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.game.world.item.ItemUpdatePacket;
import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.net.packet.Packet;
import mocha.net.packet.SimplePacketHandler;
import mocha.shared.Repository;
import mocha.shared.task.SleepyRunnable;

@Component
public class ClientPacketHandler extends SimplePacketHandler implements SleepyRunnable {

  private EventBus packetEventBus = new EventBus();
  private ConcurrentLinkedQueue<Packet> packets = Queues.newConcurrentLinkedQueue();

  private ClientEventBus clientEventBus;
  private Repository<Player, Integer> playerRepository;
  private Repository<Entity, Integer> entityRepository;
  private Repository<Chunk, Integer> chunkRepository;
  private Repository<ItemPrototype, Integer> itemPrototypeRepository;
  private Repository<Item, Integer> itemRepository;
  private TileSetFactory tileSetFactory;
  private EntityService entityService;
  private PlayerService playerService;
  private GameLogic gameLogic;

  @Inject
  public ClientPacketHandler(
      ClientEventBus clientEventBus,
      Repository<Player, Integer> playerRepository,
      Repository<Entity, Integer> entityRepository,
      Repository<Chunk, Integer> chunkRepository,
      Repository<ItemPrototype, Integer> itemPrototypeRepository,
      Repository<Item, Integer> itemRepository, TileSetFactory tileSetFactory,
      EntityService entityService,
      PlayerService playerService,
      GameLogic gameLogic
  ) {
    this.clientEventBus = clientEventBus;
    this.playerRepository = playerRepository;
    this.entityRepository = entityRepository;
    this.chunkRepository = chunkRepository;
    this.itemPrototypeRepository = itemPrototypeRepository;
    this.itemRepository = itemRepository;
    this.tileSetFactory = tileSetFactory;
    this.entityService = entityService;
    this.playerService = playerService;
    this.gameLogic = gameLogic;
  }

  @Override
  public void run() {
    packetEventBus.register(this);
    //noinspection InfiniteLoopStatement
    while (true) {
      if (!packets.isEmpty()) {
        Packet packet = packets.poll();
        packetEventBus.post(packet);
        nap();
      }
    }
  }

  @Override
  public void handle(int senderId, Packet packet) {
    packets.offer(packet);
  }

  @Subscribe
  public void handle(LoginSuccessPacket loginSuccessPacket) {
    playerService.addPlayer(new LocalPlayer(loginSuccessPacket.getPlayerId()));
  }

  @Subscribe
  public void handle(PlayerIdentityPacket playerIdentityPacket) {

  }

  @Subscribe
  public void handle(RequestEntitiesByPlayerIdPacket requestEntitiesByPlayerIdPacket) {
    playerRepository.findById(requestEntitiesByPlayerIdPacket.getPlayerId())
        .ifPresent(player -> requestEntitiesByPlayerIdPacket.getEntityIds()
            .forEach(entityId -> entityRepository.findById(entityId)
                .ifPresent(player::setEntity)));
  }

  @Subscribe
  @Override
  public void handle(ChunkUpdatePacket chunkUpdatePacket) {
    int chunkId = chunkUpdatePacket.getChunkId();
    Location location = new Location(chunkUpdatePacket.getX(), chunkUpdatePacket.getY());
    TileType[] tiles = tileSetFactory.newTilesFromString(chunkUpdatePacket.getTilesString());
    Chunk chunk = new Chunk(chunkId, location, tiles);
    chunkRepository.save(chunk);
  }

  @Subscribe
  public void handle(TileUpdatePacket tileUpdatePacket) {
    chunkRepository.findById(tileUpdatePacket.getChunkId())
        .ifPresent(chunk -> {
          chunk.setTile(tileUpdatePacket.getTileX(), tileUpdatePacket.getTileY(), tileUpdatePacket.getTileType());
          chunkRepository.save(chunk);
        });
  }

  @Subscribe
  public void handle(EntityPrototypeUpdatePacket entityPrototypeUpdatePacket) {
    gameLogic.handle(new UpdateEntityPrototypeCommand(entityPrototypeUpdatePacket.getEntityPrototype()));
  }

  @Subscribe
  @Override
  public void handle(EntityUpdatePacket entityUpdatePacket) {
    clientEventBus.postEntityUpdatedEvent(createEntity(entityUpdatePacket));
  }

  private Entity createEntity(EntityUpdatePacket entityUpdatePacket) {
    int entityId = entityUpdatePacket.getEntityId();
    Location location = entityUpdatePacket.getLocation();
    EntityType entityType = entityUpdatePacket.getEntityType();
    int typeId = entityUpdatePacket.getTypeId();

    switch (entityType) {
      case ITEM:
        Optional<Item> item = itemRepository.findById(typeId);
        return new ItemEntity(entityId, location, item.orElse(null));
      case MOB:
      default:
        return new Entity(entityId, location);
    }
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    clientEventBus.post(movePacket.getMoveCommand());
  }

  @Subscribe
  public void handle(EntityRemovedPacket entityRemovedPacket) {
    entityService.removeEntity(entityRemovedPacket.getEntityId());
  }

  @Subscribe
  public void handle(ItemPrototypeUpdatePacket itemPrototypeUpdatePacket) {
    gameLogic.handle(new UpdateItemPrototypeCommand(
        itemPrototypeUpdatePacket.getId(),
        itemPrototypeUpdatePacket.getName(),
        itemPrototypeUpdatePacket.getSpriteId(),
        itemPrototypeUpdatePacket.getItemType(),
        itemPrototypeUpdatePacket.getDescription()
    ));
  }

  @Subscribe
  public void handle(ItemUpdatePacket itemUpdatePacket) {
    itemPrototypeRepository.findById(itemUpdatePacket.getItemPrototypeId())
        .ifPresent(itemPrototype -> itemRepository.save(new Item(itemUpdatePacket.getId(), itemPrototype, itemUpdatePacket.getData0(), itemUpdatePacket.getData1(), itemUpdatePacket.getData2())));
  }

}
