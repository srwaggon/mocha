package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.net.MochaConnection;
import mocha.net.PacketHandler;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public class ServerPacketHandler implements PacketHandler {
  private MochaConnection mochaConnection;
  private PacketFactory packetFactory;

  public ServerPacketHandler(MochaConnection mochaConnection, PacketFactory packetFactory) {
    this.mochaConnection = mochaConnection;
    this.packetFactory = packetFactory;
  }

  @Override
  @Subscribe
  public void handlePacket(Packet packet) {
  }

  @Subscribe
  public void handle(ChunkRequestPacket chunkRequestPacket) {
    ChunkPacket chunkPacket = packetFactory.newChunkPacket();
    mochaConnection.sendPacket(chunkPacket);
  }
}
