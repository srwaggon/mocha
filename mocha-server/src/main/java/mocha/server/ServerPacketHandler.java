package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.net.MochaConnection;
import mocha.net.SimplePacketHandler;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;

public class ServerPacketHandler extends SimplePacketHandler {
  private MochaConnection mochaConnection;
  private PacketFactory packetFactory;
  private Game game;

  ServerPacketHandler(MochaConnection mochaConnection, PacketFactory packetFactory, Game game) {
    this.mochaConnection = mochaConnection;
    this.packetFactory = packetFactory;
    this.game = game;
  }

  @Subscribe
  @Override
  public void handle(RequestChunkPacket requestChunkPacket) {
    Location location = requestChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk ->
        mochaConnection.sendPacket(packetFactory.newChunkPacket(location, chunk)));
  }

  @Subscribe
  @Override
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {
    Location location = requestEntitiesInChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk ->
        chunk.getEntities().forEach((entity) ->
            mochaConnection.sendPacket(packetFactory.newEntityPacket(entity))));
  }
}
