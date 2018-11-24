package mocha.game.world.entity;

import mocha.net.packet.PacketType;

public class EntityRemovedPacket extends AbstractEntityPacket {

  public EntityRemovedPacket() {
  }

  public EntityRemovedPacket(int entityId) {
    super(entityId);
  }

  @Override
  public PacketType getType() {
    return PacketType.ENTITY_REMOVED;
  }
}
