package mocha.net.packet.message;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;

public class GlobalMessagePacket extends AbstractPacket implements Packet {

  public GlobalMessagePacket(String message) {
    data = new String[2];
    data[0] = getType().name();
    data[1] = message;
  }

  @Override
  public PacketType getType() {
    return PacketType.GLOBAL_MESSAGE;
  }

}
