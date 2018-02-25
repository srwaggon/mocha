package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.net.MochaConnection;
import mocha.net.SimplePacketHandler;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

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
  public void handle(ChunkRequestPacket chunkRequestPacket) {
    Location location = chunkRequestPacket.getLocation();
    game.getWorld().getChunkAt(location).ifPresent(chunk -> {
      ChunkPacket chunkPacket = packetFactory.newChunkPacket(location, chunk);
      mochaConnection.sendPacket(chunkPacket);
    });
  }
}
