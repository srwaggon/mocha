package mocha.game;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class PlayerIdentityPacket extends AbstractPacket {

  @Override
  public PacketType getType() {
    return PacketType.PLAYER_IDENTITY;
  }
}
