package mocha.net.packet.message;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;

public class GlobalMessagePacket extends AbstractPacket implements Packet {

  public GlobalMessagePacket(String message) {
    addToData(message);
  }

  @Override
  public PacketType getType() {
    return PacketType.GLOBAL_MESSAGE;
  }

}
