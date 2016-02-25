package mocha.net.packet;

public interface Packet {

  void build(String[] data);

  PacketCode getCode();

  String[] getData();

  String construct();
}
