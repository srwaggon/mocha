package mocha.net.packet;

import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.RequestEntityByIdPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

public enum PacketType {

  GLOBAL_MESSAGE(GlobalMessagePacket.class),

  REQUEST_CHUNK(RequestChunkPacket.class),
  CHUNK(ChunkPacket.class),

  REQUEST_ENTITIES_IN_CHUNK(RequestEntitiesInChunkPacket.class),
  REQUEST_ENTITY_BY_ID(RequestEntityByIdPacket.class),
  ENTITY(EntityPacket.class),

  MOVE_REQUEST(MovePacket.class)
  ;

  public static final String SEPARATOR = " ";
  private final Class<? extends Packet> packetClass;


  PacketType(Class<? extends Packet> packetClass) {
    this.packetClass = packetClass;
  }

  public static Packet resolve(Packet packet) {
    try {
      Packet resolvedPacket = packet.getType().packetClass.newInstance();
      resolvedPacket.build(packet.getData());
      return resolvedPacket;
    } catch (InstantiationException | IllegalAccessException ignored) {
      System.out.println(ignored);
    }
    return packet;
  }
}
