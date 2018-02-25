package mocha.net;

import mocha.net.packet.GlobalMessagePacket;
import mocha.net.packet.UnknownPacket;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;

public interface PacketHandler {

  void handle(ChunkPacket chunkPacket);

  void handle(RequestChunkPacket requestChunkPacket);

  void handle(UnknownPacket unknownPacket);

  void handle(GlobalMessagePacket globalMessagePacket);

}
