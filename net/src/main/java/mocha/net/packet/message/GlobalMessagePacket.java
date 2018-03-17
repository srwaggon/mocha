package mocha.net.packet.message;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;

import static mocha.net.packet.PacketType.GLOBAL_MESSAGE;

public class GlobalMessagePacket extends AbstractPacket implements Packet {

  private String message;

  public GlobalMessagePacket(String message) {
    this.message = message;
  }

  @Override
  public PacketType getType() {
    return GLOBAL_MESSAGE;
  }

  @Override
  public String[] getData() {
    String[] data = new String[2];
    data[0] = getType().name();
    data[1] = message;
    return data;
  }

  @Override
  public void build(String[] data) {
    message = data[1];
  }

}
