package mocha.net.packet;

import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public enum PacketType {

  GLOBAL_MESSAGE(GlobalMessagePacket.class),
  CHUNK_REQUEST(ChunkRequestPacket.class),
  CHUNK(ChunkPacket.class)
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
