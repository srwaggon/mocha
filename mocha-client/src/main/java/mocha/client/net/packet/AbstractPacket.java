package mocha.client.net.packet;

public abstract  class AbstractPacket implements Packet {
  public String construct() {
    return String.join(PacketType.SEPARATOR, getData());
  }
}
