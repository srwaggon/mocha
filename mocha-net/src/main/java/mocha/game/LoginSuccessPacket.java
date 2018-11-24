package mocha.game;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class LoginSuccessPacket extends AbstractPacket {

  public LoginSuccessPacket() {
  }

  public LoginSuccessPacket(int playerId) {
    data = new String[2];
    data[0] = getType().name();
    data[1] = "" + playerId;
  }

  @Override
  public PacketType getType() {
    return PacketType.LOGIN_SUCCESS;
  }

  public int getPlayerId() {
    return getDataAsInt(1);
  }
}
