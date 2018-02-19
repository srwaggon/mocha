package mocha.client.net.packet;

public enum PacketType {

  GLOBAL_MESSAGE(GlobalMessagePacket.class),
  MAP(null),
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
    }
    return packet;
  }
}
