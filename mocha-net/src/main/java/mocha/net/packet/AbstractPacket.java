package mocha.net.packet;

import java.util.Arrays;

public abstract class AbstractPacket implements Packet {

  private String data;

  public AbstractPacket() {
     data = "" + getType().name();
  }

  public AbstractPacket(String data) {
    this.data = data;
  }

  protected void addToData(String s) {
    data += data.isEmpty() ? s : PacketType.SEPARATOR + s;
  }

  protected void addToData(char c) {
    addToData("" + c);
  }

  protected void addToData(int i) {
    addToData("" + i);
  }

  public String construct() {
    return String.join(PacketType.SEPARATOR, getSplitData());
  }

  protected int getDataAsInt(int index) {
    return Integer.parseInt(getSplitData()[index]);
  }

  protected char getDataAsChar(int index) {
    return getSplitData()[index].charAt(0);
  }

  @Override
  public void build(String data) {
    this.data = data;
  }

  protected String[] getSplitData() {
    return data.split(PacketType.SEPARATOR);
  }

  @Override
  public String getData() {
    return data;
  }

  @Override
  public String toString() {
    return Arrays.toString(getSplitData());
  }
}
