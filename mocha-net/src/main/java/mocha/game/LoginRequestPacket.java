package mocha.game;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class LoginRequestPacket extends AbstractPacket {

  public LoginRequestPacket() {
  }

  public LoginRequestPacket(String accountName) {
    addToData(accountName);
  }

  @Override
  public PacketType getType() {
    return PacketType.LOGIN_REQUEST;
  }
}
