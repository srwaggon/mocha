package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.player.PlayerService;
import mocha.game.player.event.PlayerAddedEvent;
import mocha.game.player.event.PlayerRemovedEvent;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.event.ChunkUpdatedEvent;
import mocha.game.world.chunk.tile.event.TileUpdatedEvent;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.UpdateItemCommand;
import mocha.net.event.DisconnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;

@Component
public class ServerGameLogic implements GameLogic {

  private Logger log = LoggerFactory.getLogger(ServerGameLogic.class);
  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  private ServerEventBus eventBus;
  private Repository<Movement, Integer> movementRepository;
  private PlayerService playerService;
  private ItemService itemService;
  private EntityPrototypeService entityPrototypeService;

  @Inject
  public ServerGameLogic(
      ServerEventBus eventBus,
      Repository<Movement, Integer> movementRepository,
      PlayerService playerService,
      ItemService itemService,
      EntityPrototypeService entityPrototypeService,
      List<MochaEventHandler> mochaEventHandlers,
      List<CommandHandler> commandHandlers,
      Map<Integer, MochaConnection> mochaConnectionsByPlayerId
  ) {
    this.eventBus = eventBus;
    this.movementRepository = movementRepository;
    this.playerService = playerService;
    this.itemService = itemService;
    this.entityPrototypeService = entityPrototypeService;
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
    mochaEventHandlers.forEach(eventBus::register);
    commandHandlers.forEach(eventBus::register);
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
    EntityMoveCommand entityMove = new EntityMoveCommand(
        movement.getId(),
        movement.getLocation(),
        movement.getDirection(),
        movement.getXOffset(),
        movement.getYOffset()
    );
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

  @Override
  public void handle(UpdateItemCommand updateItemCommand) {
    Item update = itemService.updateItem(updateItemCommand);
    getConnections().forEach(mochaConnection -> mochaConnection.sendItemUpdate(update));
  }

  @Override
  public void handle(UpdateEntityPrototypeCommand updateEntityPrototypeCommand) {
    entityPrototypeService.save(updateEntityPrototypeCommand.getEntityPrototype());
  }

}
