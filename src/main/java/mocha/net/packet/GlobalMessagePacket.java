package mocha.net.packet;

import static mocha.net.packet.PacketCode.*;

public class GlobalMessagePacket implements Packet {

  private String message;

  public GlobalMessagePacket() {

  }

  public GlobalMessagePacket(String message) {
    this.message = message;
  }

  @Override
  public PacketCode getCode() {
    return GLOBAL_MESSAGE;
  }

  @Override
  public String construct() {
    return getCode().name() + SEPARATOR + message;
  }

  @Override
  public String[] getData() {
    String[] data = new String[2];
    data[0] = getCode().name();
    data[1] = message;
    return data;
  }

  @Override
  public void build(String[] data) {
    message = data[1];
  }

}
