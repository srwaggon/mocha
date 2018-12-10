package mocha.game;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.player.event.PlayerAddedEvent;
import mocha.game.player.event.PlayerRemovedEvent;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.chunk.event.ChunkUpdatedEvent;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemPrototype;
import mocha.game.world.tile.event.TileUpdatedEvent;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;
import mocha.shared.Repository;

@Component
public class ServerGameLogic implements GameLogic {

  private Logger log = LoggerFactory.getLogger(ServerGameLogic.class);
  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId = Maps.newConcurrentMap();

  private ServerEventBus eventBus;
  private IdFactory<Player> playerIdFactory;
  private PlayerFactory playerFactory;
  private Repository<Movement, Integer> movementRepository;
  private MovementFactory movementFactory;
  private EntitiesInChunkService entitiesInChunkService;
  private ChunkService chunkService;
  private IdFactory<Entity> entityIdFactory;
  private EntityService entityService;
  private PlayerService playerService;
  private Repository<Item, Integer> itemRepository;
  private Repository<ItemPrototype, Integer> itemPrototypeRepository;

  @Inject
  public ServerGameLogic(
      ServerEventBus eventBus,
      IdFactory<Player> playerIdFactory,
      PlayerFactory playerFactory,
      Repository<Movement, Integer> movementRepository,
      MovementFactory movementFactory,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      IdFactory<Entity> entityIdFactory,
      EntityService entityService,
      PlayerService playerService,
      Repository<Item, Integer> itemRepository,
      Repository<ItemPrototype, Integer> itemPrototypeRepository
  ) {
    this.eventBus = eventBus;
    this.playerIdFactory = playerIdFactory;
    this.playerFactory = playerFactory;
    this.movementRepository = movementRepository;
    this.movementFactory = movementFactory;
    this.entitiesInChunkService = entitiesInChunkService;
    this.chunkService = chunkService;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
    this.playerService = playerService;
    this.itemRepository = itemRepository;
    this.itemPrototypeRepository = itemPrototypeRepository;
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());

    MochaConnection playerConnection = connectedEvent.getMochaConnection();

    int playerId = playerIdFactory.newId();
    Entity playerEntity = entityService.addEntity(new Entity(entityIdFactory.newId()));
    movementRepository.save(movementFactory.newSlidingMovement(playerEntity));
    NetworkPlayer player = playerFactory.newNetworkPlayer(playerConnection, playerId, playerEntity);
    mochaConnectionsByPlayerId.put(playerId, playerConnection);
    playerService.addPlayer(player);

    itemPrototypeRepository.findAll()
        .forEach(playerConnection::sendItemPrototypeUpdate);
    itemRepository.findAll()
        .forEach(playerConnection::sendItemUpdate);


    Chunk playerChunk = getPlayerChunk(player);
    playerConnection.sendChunkUpdate(playerChunk);
    entitiesInChunkService.getEntitiesInChunk(playerChunk)
        .forEach(playerConnection::sendEntityUpdate);

    playerConnection.sendLoginSuccessful(playerId);
  }

  private Chunk getPlayerChunk(Player player) {
    Entity playerEntity = player.getEntity();
    Location playerEntityLocation = playerEntity.getLocation();
    return chunkService.getOrCreateChunkAt(playerEntityLocation);
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
    playerService.removePlayer(disconnectedEvent.getSenderId());
  }

  @Subscribe
  public void handle(PlayerAddedEvent playerAddedEvent) {

  }

  @Subscribe
  public void handle(PlayerRemovedEvent playerRemovedEvent) {

  }

  @Subscribe
  public void handle(ChunkUpdatedEvent chunkUpdatedEvent) {
    Chunk chunk = chunkUpdatedEvent.getChunk();
    getConnections().forEach(mochaConnection -> mochaConnection.sendChunkUpdate(chunk));
  }

  @Subscribe
  public void handle(TileUpdatedEvent tileUpdatedEvent) {
    Chunk chunk = tileUpdatedEvent.getChunk();
    int x = tileUpdatedEvent.getX();
    int y = tileUpdatedEvent.getY();
    getConnections().forEach(mochaConnection ->
        mochaConnection.sendTileUpdate(chunk, x, y));
  }

  @Subscribe
  public void handle(EntityAddedEvent entityAddedEvent) {
    getConnections().forEach(mochaConnection ->
        mochaConnection.sendEntityUpdate(entityAddedEvent.getEntity()));
  }

  @Subscribe
  public void handle(EntityRemovedEvent entityRemovedEvent) {
    getConnections().forEach(mochaConnection ->
        mochaConnection.sendEntityRemoved(entityRemovedEvent.getEntity()));
  }

  @Subscribe
  public void handle(EntityMovementEvent entityMovementEvent) {
    Movement movement = entityMovementEvent.getMovement();
    EntityMoveCommand entityMove = EntityMoveCommand.builder()
        .entityId(movement.getId())
        .location(movement.getLocation())
        .direction(movement.getDirection())
        .xOffset(movement.getXOffset())
        .yOffset(movement.getYOffset())
        .build();
    getConnections().forEach(mochaConnection -> mochaConnection.sendMoveCommand(entityMove));
  }

  private Collection<MochaConnection> getConnections() {
    return mochaConnectionsByPlayerId.values();
  }

  @Subscribe
  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    movementRepository.findById(entityMoveCommand.getEntityId())
        .ifPresent(movement -> {
          movement.handle(entityMoveCommand);
          eventBus.postMoveEvent(movement);
        });
  }
}
