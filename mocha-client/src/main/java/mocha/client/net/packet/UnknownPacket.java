package mocha.client.net.packet;

public class UnknownPacket implements Packet {

  private String[] data;

  public UnknownPacket(String data) {
    this.data = data.split(PacketType.SEPARATOR);
  }

  @Override
  public void build(String[] data) {
    this.data = data;
  }

  @Override
  public PacketType getType() {
    return PacketType.valueOf(data[0]);
  }

  @Override
  public String[] getData() {
    return data;
  }

  @Override
  public String construct() {
    return String.join(PacketType.SEPARATOR, data);
  }
}
