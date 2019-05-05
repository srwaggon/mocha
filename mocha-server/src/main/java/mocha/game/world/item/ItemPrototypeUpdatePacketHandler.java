package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

public class ItemPrototypeUpdatePacketHandler implements PacketHandler<ItemPrototypeUpdatePacket> {

  private ServerEventBus serverEventBus;

  public ItemPrototypeUpdatePacketHandler(ServerEventBus serverEventBus) {
    this.serverEventBus = serverEventBus;
  }

  @Subscribe
  public void handle(ItemPrototypeUpdatePacket itemPrototypeUpdatePacket) {
    serverEventBus.post(new UpdateItemPrototypeCommand(
        itemPrototypeUpdatePacket.getId(),
        itemPrototypeUpdatePacket.getName(),
        itemPrototypeUpdatePacket.getSpriteId(),
        itemPrototypeUpdatePacket.getItemType(),
        itemPrototypeUpdatePacket.getDescription()
    ));
  }
}