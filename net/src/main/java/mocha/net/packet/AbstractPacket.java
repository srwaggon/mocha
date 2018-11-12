package mocha.net.packet;

import java.util.Arrays;

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

  @Override
  public String toString() {
    return Arrays.toString(data);
  }
}
