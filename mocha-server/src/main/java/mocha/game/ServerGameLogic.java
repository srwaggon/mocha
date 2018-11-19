package mocha.game;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;

@Component
public class ServerGameLogic implements GameLogic {

  private Logger log = LoggerFactory.getLogger(ServerGameLogic.class);

  @Inject
  private ServerEventBus eventBus;

  @Inject
  private Game game;

  @Inject
  private PlayerFactory playerFactory;

  private List<MochaConnection> mochaConnections = Lists.newArrayList();

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());

    MochaConnection mochaConnection = connectedEvent.getMochaConnection();
    mochaConnections.add(mochaConnection);
    game.addPlayer(playerFactory.newNetworkPlayer(mochaConnection));
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
    game.removePlayer(disconnectedEvent.getSenderId());
  }

  @Subscribe
  public void handle(EntityAddedEvent entityAddedEvent) {
    mochaConnections.forEach(mochaConnection ->
        mochaConnection.sendEntityUpdate(entityAddedEvent.getEntity()));
  }

  @Subscribe
  public void handle(EntityRemovedEvent entityRemovedEvent) {
    mochaConnections.forEach(mochaConnection ->
        mochaConnection.sendEntityRemoved(entityRemovedEvent.getEntity()));
  }

  @Subscribe
  public void handle(EntityMovementEvent entityMovementEvent) {
    Movement movement = entityMovementEvent.getMovement();
    EntityMoveCommand entityMove = EntityMoveCommand.builder()
        .entityId(movement.getEntity().getId())
        .location(movement.getLocation())
        .direction(movement.getDirection())
        .xOffset(movement.getXOffset())
        .yOffset(movement.getYOffset())
        .build();
    mochaConnections.forEach(mochaConnection -> mochaConnection.sendMoveCommand(entityMove));
  }

  @Subscribe
  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    game.getEntityRepository()
        .findById(entityMoveCommand.getEntityId())
        .map(Entity::getMovement)
        .ifPresent(movement -> {
          movement.handle(entityMoveCommand);
          eventBus.postMoveEvent(movement);
        });
  }
}
