package mocha.net.packet;

public interface Packet {

  void build(String data);

  PacketType getType();

  String[] getSplitData();

  String getData();

  String construct();

  String getData(int index);

  char getDataAsChar(int index);

  int getDataAsInt(int index);

  boolean getDataAsBoolean(int index);

  double getDataAsDouble(int index);
}
