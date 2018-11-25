package mocha.game.world.item;

import mocha.game.world.entity.EntityUpdatePacket;
import mocha.net.packet.PacketType;

public class ItemEntityUpdatePacket extends EntityUpdatePacket {
  public ItemEntityUpdatePacket() {
  }

  public ItemEntityUpdatePacket(ItemEntity entity) {
    super(entity);
    addToData(entity.getItem().getId());
  }

  @Override
  public PacketType getType() {
    return PacketType.ITEM_ENTITY_UPDATE;
  }
}
