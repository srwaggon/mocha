package mocha.net;

import mocha.net.packet.UnknownPacket;
import mocha.net.packet.message.GlobalMessagePacket;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.RequestEntityByIdPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

public class SimplePacketHandler implements PacketHandler {
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
