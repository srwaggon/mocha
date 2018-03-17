package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.net.MochaConnection;
import mocha.net.SimplePacketHandler;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

public class ServerPacketHandler extends SimplePacketHandler {
  private MochaConnection mochaConnection;
  private PacketFactory packetFactory;
  private Game game;
  private NetworkedMochaEventBus networkedMochaEventBus;

  ServerPacketHandler(MochaConnection mochaConnection, PacketFactory packetFactory, Game game, NetworkedMochaEventBus networkedMochaEventBus) {
    this.mochaConnection = mochaConnection;
    this.packetFactory = packetFactory;
    this.game = game;
    this.networkedMochaEventBus = networkedMochaEventBus;
  }

  @Subscribe
  public void handleMoveEvent(EntityMovementEvent entityMovementEvent) {
    mochaConnection.sendPacket(packetFactory.entityPacket(entityMovementEvent.getMovement().getEntity()));
  }

  @Subscribe
  @Override
  public void handle(RequestChunkPacket requestChunkPacket) {
    Location location = requestChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk ->
        sendChunkUpdate(location, chunk));
  }

  @Subscribe
  @Override
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {
    Location location = requestEntitiesInChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk ->
        chunk.getEntities().forEach(this::sendEntityUpdate));
  }

  private void sendChunkUpdate(Location location, Chunk chunk) {
    mochaConnection.sendPacket(packetFactory.newChunkPacket(location, chunk));
  }

  private void sendEntityUpdate(Entity entity) {
    mochaConnection.sendPacket(packetFactory.entityPacket(entity));
  }

  @Subscribe
  @Override
  public void handle(MovePacket movePacket) {
    networkedMochaEventBus.post(movePacket.getMoveCommand());
  }
}
