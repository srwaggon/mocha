package mocha.game;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.PlayerAddedEvent;
import mocha.game.event.PlayerRemovedEvent;
import mocha.game.item.ItemPrototypeRepository;
import mocha.game.item.ItemRepository;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.event.ChunkUpdatedEvent;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
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
  private Game game;
  private IdFactory<Player> playerIdFactory;
  private PlayerFactory playerFactory;
  private Repository<Movement, Integer> movementRepository;
  private MovementFactory movementFactory;
  private ItemPrototypeRepository itemPrototypeRepository;
  private ItemRepository itemRepository;


  @Inject
  public ServerGameLogic(
      ServerEventBus eventBus,
      Game game,
      IdFactory<Player> playerIdFactory,
      PlayerFactory playerFactory,
      Repository<Movement, Integer> movementRepository,
      MovementFactory movementFactory,
      ItemPrototypeRepository itemPrototypeRepository, ItemRepository itemRepository) {
    this.eventBus = eventBus;
    this.game = game;
    this.playerIdFactory = playerIdFactory;
    this.playerFactory = playerFactory;
    this.movementRepository = movementRepository;
    this.movementFactory = movementFactory;
    this.itemPrototypeRepository = itemPrototypeRepository;
    this.itemRepository = itemRepository;
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());

    MochaConnection mochaConnection = connectedEvent.getMochaConnection();

    int playerId = playerIdFactory.newId();
    Entity entity = game.addEntity(new Entity());
    movementRepository.save(movementFactory.newSlidingMovement(entity));
    NetworkPlayer player = playerFactory.newNetworkPlayer(mochaConnection, playerId, entity);
    mochaConnectionsByPlayerId.put(playerId, mochaConnection);
    game.addPlayer(player);

    itemPrototypeRepository.findAll().forEach(mochaConnection::sendItemPrototypeUpdate);
    itemRepository.findAll().forEach(mochaConnection::sendItemUpdate);

    mochaConnection.sendLoginSuccessful(playerId);
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
    game.removePlayer(disconnectedEvent.getSenderId());
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
