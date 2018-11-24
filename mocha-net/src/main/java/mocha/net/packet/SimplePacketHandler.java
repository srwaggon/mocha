package mocha.net.packet;

import mocha.net.packet.message.GlobalMessagePacket;
import mocha.game.world.chunk.ChunkPacket;
import mocha.game.world.chunk.RequestChunkPacket;
import mocha.game.world.entity.EntityPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;

public class SimplePacketHandler implements PacketHandler {
  @Override
  public void handle(int senderId, Packet packet) {

  }

  @Override
  public void handle(ChunkPacket chunkPacket) {

  }

  @Override
  public void handle(RequestChunkPacket requestChunkPacket) {

  }

  @Override
  public void handle(UnknownPacket unknownPacket) {

  }

  @Override
  public void handle(GlobalMessagePacket globalMessagePacket) {

  }

  @Override
  public void handle(RequestEntityByIdPacket requestEntityByIdPacket) {

  }

  @Override
  public void handle(EntityPacket entityPacket) {

  }

  @Override
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {

  }

  @Override
  public void handle(MovePacket movePacket) {

  }
}
