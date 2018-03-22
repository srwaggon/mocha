package mocha.net.packet;

import mocha.net.packet.message.GlobalMessagePacket;
import mocha.game.world.chunk.ChunkPacket;
import mocha.game.world.chunk.RequestChunkPacket;
import mocha.game.world.entity.EntityPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;

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
