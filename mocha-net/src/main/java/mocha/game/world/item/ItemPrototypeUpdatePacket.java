package mocha.game.world.item;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ItemPrototypeUpdatePacket extends AbstractPacket {

  public ItemPrototypeUpdatePacket() {
  }

  public ItemPrototypeUpdatePacket(ItemPrototype itemPrototype) {
    addToData(itemPrototype.getId());
    addToData(itemPrototype.getName());
    addToData(itemPrototype.getSpriteId());
    addToData(itemPrototype.getItemType().name());
    addToData(itemPrototype.getDescription());
  }

  @Override
  public PacketType getType() {
    return PacketType.ITEM_PROTOTYPE_UPDATE;
  }
}
