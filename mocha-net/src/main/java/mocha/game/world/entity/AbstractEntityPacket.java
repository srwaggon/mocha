package mocha.game.world.entity;

import mocha.net.packet.AbstractPacket;

public abstract class AbstractEntityPacket extends AbstractPacket {

  public AbstractEntityPacket() {
  }

  public AbstractEntityPacket(int entityId) {
    addToData(entityId);
  }

  public int getEntityId() {
    return getDataAsInt(1);
  }
}
