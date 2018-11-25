package mocha.net.packet;

public interface Packet {

  void build(String data);

  PacketType getType();

  String getData();

  String construct();
}
