package mocha.net.packet;

public enum PacketCode {

  GLOBAL_MESSAGE(GlobalMessagePacket.class),
  ;

  public static final String SEPARATOR = " ";
  private final Class<? extends Packet> packetClass;


  PacketCode(Class<? extends Packet> packetClass) {
    this.packetClass = packetClass;
  }

  public static Packet resolve(Packet packet) {
    try {
      Packet resolvedPacket = packet.getCode().packetClass.newInstance();
      resolvedPacket.build(packet.getData());
      return resolvedPacket;
    } catch (InstantiationException | IllegalAccessException ignored) {
    }
    return packet;
  }
}
