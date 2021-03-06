package mocha.game.world.item;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ItemUpdatePacket extends AbstractPacket {

  public ItemUpdatePacket() {
  }

  public ItemUpdatePacket(Item item) {
    addToData(item.getId());
    addToData(item.getItemPrototype().getId());
    addToData(item.getData0());
    addToData(item.getData1());
    addToData(item.getData2());
  }

  @Override
  public PacketType getType() {
    return PacketType.ITEM_UPDATE;
  }

  public int getId() {
    return getDataAsInt(1);
  }

  public int getItemPrototypeId() {
    return getDataAsInt(2);
  }

  public int getData0() {
    return getDataAsInt(3);
  }

  public int getData1() {
    return getDataAsInt(4);
  }

  public int getData2() {
    return getDataAsInt(5);
  }
}
