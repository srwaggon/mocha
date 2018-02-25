package mocha.net;

import mocha.net.packet.GlobalMessagePacket;
import mocha.net.packet.UnknownPacket;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public class SimplePacketHandler implements PacketHandler {
  @Override
  public void handle(ChunkPacket chunkPacket) {

  }

  @Override
  public void handle(ChunkRequestPacket chunkRequestPacket) {

  }

  @Override
  public void handle(UnknownPacket unknownPacket) {

  }

  @Override
  public void handle(GlobalMessagePacket globalMessagePacket) {

  }
}
