package mocha.game.world.item;

import mocha.game.world.item.itemprototype.ItemPrototype;
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

  public int getId() {
    return getDataAsInt(1);
  }

  public String getName() {
    return getData(2);
  }

  public String getSpriteId() {
    return getData(3);
  }

  public ItemType getItemType() {
    return ItemType.valueOf(getData(4));
  }

  public String getDescription() {
    return getData(5);
  }

  public ItemPrototype getItemPrototype() {
    return new ItemPrototype(getId(), getName(), getSpriteId(), getItemType(), getDescription());
  }
}
