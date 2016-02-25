package mocha.net.packet;

public class UnknownPacket implements Packet {

  private String[] data;

  public UnknownPacket(String data) {
    this.data = data.split(PacketCode.SEPARATOR);
  }

  @Override
  public void build(String[] data) {
    this.data = data;
  }

  @Override
  public PacketCode getCode() {
    return PacketCode.valueOf(data[0]);
  }

  @Override
  public String[] getData() {
    return data;
  }

  @Override
  public String construct() {
    return String.join(PacketCode.SEPARATOR, data);
  }
}
