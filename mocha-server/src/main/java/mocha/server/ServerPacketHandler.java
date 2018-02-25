package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.net.MochaConnection;
import mocha.net.SimplePacketHandler;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;

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
  public void handle(RequestChunkPacket requestChunkPacket) {
    Location location = requestChunkPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk -> {
      ChunkPacket chunkPacket = packetFactory.newChunkPacket(location, chunk);
      mochaConnection.sendPacket(chunkPacket);
    });
  }
}
