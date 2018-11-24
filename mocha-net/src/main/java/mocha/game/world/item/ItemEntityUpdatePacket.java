package mocha.game.world.item;

import java.util.Arrays;

import mocha.game.world.entity.EntityUpdatePacket;
import mocha.net.packet.PacketType;

public class ItemEntityUpdatePacket extends EntityUpdatePacket {
  public ItemEntityUpdatePacket() {
  }

  public ItemEntityUpdatePacket(ItemEntity entity) {
    super(entity);
    data = Arrays.copyOf(data, 5);
    data[0] = getType().name();
    data[4] = "" + entity.getItem().getId();
  }

  @Override
  public PacketType getType() {
    return PacketType.ITEM_ENTITY_UPDATE;
  }
}
