package mocha.net;

import mocha.net.packet.GlobalMessagePacket;
import mocha.net.packet.UnknownPacket;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.RequestEntityByIdPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

public interface PacketHandler {

  void handle(ChunkPacket chunkPacket);

  void handle(RequestChunkPacket requestChunkPacket);

  void handle(UnknownPacket unknownPacket);

  void handle(GlobalMessagePacket globalMessagePacket);

  void handle(RequestEntityByIdPacket requestEntityByIdPacket);

  void handle(EntityPacket entityPacket);

  void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket);

  void handle(MovePacket movePacket);

}
