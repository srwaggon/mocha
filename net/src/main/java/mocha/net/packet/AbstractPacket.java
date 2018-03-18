package mocha.net.packet;

public abstract  class AbstractPacket implements Packet {
  public String construct() {
    return String.join(PacketType.SEPARATOR, getData());
  }

  protected int getDataAsInt(int index) {
    return Integer.parseInt(getData()[index]);
  }
}
