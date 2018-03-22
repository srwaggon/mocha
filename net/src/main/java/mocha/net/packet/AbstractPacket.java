package mocha.net.packet;

public abstract class AbstractPacket implements Packet {

  protected String[] data;

  public String construct() {
    return String.join(PacketType.SEPARATOR, getData());
  }

  protected int getDataAsInt(int index) {
    return Integer.parseInt(getData()[index]);
  }

  @Override
  public void build(String[] data) {
    this.data = data;
  }

  @Override
  public String[] getData() {
    return data;
  }
}
