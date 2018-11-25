package mocha.net.packet;

public class UnknownPacket extends AbstractPacket {

  UnknownPacket(String data) {
    super(data);
  }

  @Override
  public PacketType getType() {
    return PacketType.valueOf(getSplitData()[0]);
  }

}
