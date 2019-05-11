package mocha.net;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class LoginSuccessPacket extends AbstractPacket {

  public LoginSuccessPacket() {
  }

  public LoginSuccessPacket(int playerId) {
    addToData(playerId);
  }

  @Override
  public PacketType getType() {
    return PacketType.LOGIN_SUCCESS;
  }

  public int getPlayerId() {
    return getDataAsInt(1);
  }
}
