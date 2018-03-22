package mocha.game.world.entity;

import mocha.net.packet.AbstractPacket;

public abstract class AbstractEntityPacket extends AbstractPacket {

  public AbstractEntityPacket() {
  }

  public AbstractEntityPacket(int entityId) {
    data = new String[2];
    data[0] = getType().name();
    data[1] = "" + entityId;
  }

  public int getId() {
    return getDataAsInt(1);
  }
}
