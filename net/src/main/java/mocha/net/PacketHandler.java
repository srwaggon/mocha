package mocha.net;

import mocha.net.packet.GlobalMessagePacket;
import mocha.net.packet.UnknownPacket;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public interface PacketHandler {

  void handle(ChunkPacket chunkPacket);

  void handle(ChunkRequestPacket chunkRequestPacket);

  void handle(UnknownPacket unknownPacket);

  void handle(GlobalMessagePacket globalMessagePacket);

}
