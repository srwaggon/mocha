package mocha.game.world.item;

import mocha.game.world.entity.AbstractEntityPacket;
import mocha.net.packet.PacketType;

public class PickUpItemPacket extends AbstractEntityPacket {
  public PickUpItemPacket() {
  }

  public PickUpItemPacket(int entityId) {
    super(entityId);
  }

  @Override
  public PacketType getType() {
    return PacketType.PICKUP_ITEM;
  }
}
