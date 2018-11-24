package mocha.game.world.item;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ItemUpdatePacket extends AbstractPacket {

  public ItemUpdatePacket() {
  }

  public ItemUpdatePacket(Item item) {
    data = new String[6];
    data[0] = "" + getType().name();
    data[1] = "" + item.getId();
    data[2] = "" + item.getItemPrototype().getId();
    data[3] = "" + item.getData0();
    data[4] = "" + item.getData1();
    data[5] = "" + item.getData2();
  }


  @Override
  public PacketType getType() {
    return PacketType.ITEM_UPDATE;
  }
}
