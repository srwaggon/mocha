package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import mocha.game.LoginRequestPacket;
import mocha.game.player.PlayerService;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;
import mocha.shared.IdFactory;
import mocha.shared.Repository;

public class LoginRequestPacketHandler implements PacketHandler<LoginRequestPacket> {
  private Logger log = LoggerFactory.getLogger(ConnectedEventHandler.class);

  private MochaConnection mochaConnection;
  private Repository<Movement, Integer> movementRepository;
  private MovementFactory movementFactory;
  private EntitiesInChunkService entitiesInChunkService;
  private ChunkService chunkService;
  private IdFactory<Entity> entityIdFactory;
  private EntityService entityService;
  private PlayerService playerService;
  private ItemService itemService;
  private ItemPrototypeService itemPrototypeService;
  private EntityPrototypeService entityPrototypeService;
  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  LoginRequestPacketHandler(
      MochaConnection mochaConnection,
      Repository<Movement, Integer> movementRepository,
      MovementFactory movementFactory,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      IdFactory<Entity> entityIdFactory,
      EntityService entityService,
      PlayerService playerService,
      ItemService itemService,
      ItemPrototypeService itemPrototypeService,
      EntityPrototypeService entityPrototypeService,
      Map<Integer, MochaConnection> mochaConnectionsByPlayerId
  ) {
    this.mochaConnection = mochaConnection;
    this.movementRepository = movementRepository;
    this.movementFactory = movementFactory;
    this.entitiesInChunkService = entitiesInChunkService;
    this.chunkService = chunkService;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
    this.playerService = playerService;
    this.itemService = itemService;
    this.itemPrototypeService = itemPrototypeService;
    this.entityPrototypeService = entityPrototypeService;
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }


  private Optional<Integer> findPlayerIdByConnection(MochaConnection mochaConnection) {
    return mochaConnectionsByPlayerId.entrySet().stream()
        .filter(pair -> mochaConnection.equals(pair.getValue()))
        .map(Map.Entry::getKey)
        .findFirst();
  }

  @Subscribe
  @Override
  public void handle(LoginRequestPacket loginRequestPacket) {
    log.info(loginRequestPacket.toString());
    // can we wait this long to create the player id?

    findPlayerIdByConnection(mochaConnection).ifPresent(playerId ->
        playerService.findById(playerId).ifPresent(player -> {
          Entity playerEntity = entityService.save(new Entity(entityIdFactory.newId()));
          movementRepository.save(movementFactory.newSlidingMovement(playerEntity));

          playerService.addEntityToPlayer(playerEntity, player);

          sendEntityPrototypes(mochaConnection);
          sendItemPrototypes(mochaConnection);
          sendItems(mochaConnection);
          sendChunk(mochaConnection, playerEntity);
          sendLoginSuccess(mochaConnection, playerId);
        }));
  }


  private void sendEntityPrototypes(MochaConnection playerConnection) {
    entityPrototypeService.findAll().forEach(playerConnection::sendEntityPrototypeUpdate);
  }

  private void sendItemPrototypes(MochaConnection playerConnection) {
    itemPrototypeService.findAll().forEach(playerConnection::sendItemPrototypeUpdate);
  }

  private void sendItems(MochaConnection playerConnection) {
    itemService.findAll().forEach(playerConnection::sendItemUpdate);
  }

  private void sendChunk(MochaConnection playerConnection, Entity playerEntity) {
    Chunk playerChunk = getPlayerChunk(playerEntity);
    playerConnection.sendChunkUpdate(playerChunk);
    sendEntitiesInChunk(playerConnection, playerChunk);
  }

  private void sendEntitiesInChunk(MochaConnection playerConnection, Chunk chunk) {
    entitiesInChunkService.getEntitiesInChunk(chunk)
        .forEach(playerConnection::sendEntityUpdate);
  }

  private void sendLoginSuccess(MochaConnection playerConnection, int playerId) {
    playerConnection.sendLoginSuccessful(playerId);
  }

  private Chunk getPlayerChunk(Entity playerEntity) {
    Location playerEntityLocation = playerEntity.getLocation();
    return chunkService.getOrCreateChunkAt(playerEntityLocation);
  }
}
