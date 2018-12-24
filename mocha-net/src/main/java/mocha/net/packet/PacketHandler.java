package mocha.net.packet;

import mocha.game.world.chunk.ChunkUpdatePacket;
import mocha.game.world.chunk.RequestChunkByLocationPacket;
import mocha.game.world.entity.EntityUpdatePacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.net.packet.message.GlobalMessagePacket;

public interface PacketHandler {

  void handle(int senderId, Packet packet);

  void handle(ChunkUpdatePacket chunkUpdatePacket);

  void handle(RequestChunkByLocationPacket requestChunkByLocationPacket);

  void handle(UnknownPacket unknownPacket);

  void handle(GlobalMessagePacket globalMessagePacket);

  void handle(RequestEntityByIdPacket requestEntityByIdPacket);

  void handle(EntityUpdatePacket entityUpdatePacket);

  void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket);

  void handle(MovePacket movePacket);

}
