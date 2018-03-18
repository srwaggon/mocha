package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.SimplePacketHandler;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

public class ServerPacketHandler extends SimplePacketHandler {
  private MochaConnection mochaConnection;
  private Game game;
  private NetworkedMochaEventBus eventBus;

  ServerPacketHandler(MochaConnection mochaConnection, Game game, NetworkedMochaEventBus eventBus) {
    this.mochaConnection = mochaConnection;
    this.game = game;
    this.eventBus = eventBus;
  }

  @Subscribe
  public void handleMoveEvent(EntityMovementEvent entityMovementEvent) {
    Movement movement = entityMovementEvent.getMovement();
    EntityMoveCommand entityMove = EntityMoveCommand.builder()
        .entityId(movement.getEntity().getId())
        .direction(movement.getDirection())
        .xOffset(movement.getXOffset())
        .yOffset(movement.getYOffset())
        .build();
    mochaConnection.sendMoveCommand(entityMove);
  }

  @Subscribe
  @Override
  public void handle(RequestChunkPacket requestChunkPacket) {
    Location location = requestChunkPacket.getLocation();
    game.getWorld().getChunkAt(location)
        .ifPresent(chunk -> mochaConnection.sendChunkUpdate(location, chunk));
  }

  @Subscribe
  @Override
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {
    Location location = requestEntitiesInChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk ->
        chunk.getEntities().forEach(mochaConnection::sendEntityUpdate));
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    eventBus.post(movePacket.getMoveCommand());
  }
}
