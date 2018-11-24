package mocha.game.world.item;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ItemPrototypeUpdatePacket extends AbstractPacket {

  public ItemPrototypeUpdatePacket() {
  }

  public ItemPrototypeUpdatePacket(ItemPrototype itemPrototype) {
    data = new String[6];
    data[0] = getType().name();
    data[1] = "" + itemPrototype.getId();
    data[2] = "" + itemPrototype.getName();
    data[3] = "" + itemPrototype.getSpriteId();
    data[4] = "" + itemPrototype.getItemType();
    data[5] = "" + itemPrototype.getDescription();
  }

  @Override
  public PacketType getType() {
    return PacketType.ITEM_PROTOTYPE_UPDATE;
  }
}
