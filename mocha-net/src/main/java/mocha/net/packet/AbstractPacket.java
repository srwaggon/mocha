package mocha.net.packet;

import java.util.Arrays;

public abstract class AbstractPacket implements Packet {

  private String data = "";

  public AbstractPacket() {
    addToData(getType().name());
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

  protected void addToData(double d) {
    addToData("" + d);
  }

  protected void addToData(boolean b) {
    addToData("" + b);
  }

  public String construct() {
    return String.join(PacketType.SEPARATOR, getSplitData());
  }

  @Override
  public void build(String data) {
    this.data = data;
  }

  @Override
  public String[] getSplitData() {
    return data.split(PacketType.SEPARATOR);
  }

  @Override
  public String getData() {
    return data;
  }

  @Override
  public String getData(int index) {
    return getSplitData()[index];
  }

  @Override
  public char getDataAsChar(int index) {
    return getData(index).charAt(0);
  }

  @Override
  public int getDataAsInt(int index) {
    return Integer.parseInt(getData(index));
  }

  @Override
  public boolean getDataAsBoolean(int index) {
    return Boolean.parseBoolean(getData(index));
  }

  @Override
  public double getDataAsDouble(int index) {
    return Double.parseDouble(getData(index));
  }

  @Override
  public String toString() {
    return Arrays.toString(getSplitData());
  }
}
