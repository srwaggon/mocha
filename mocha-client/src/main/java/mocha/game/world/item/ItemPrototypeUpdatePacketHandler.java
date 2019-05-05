package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.net.packet.PacketHandler;

@Component
public class ItemPrototypeUpdatePacketHandler implements PacketHandler<ItemPrototypeUpdatePacket> {

  private ClientEventBus clientEventBus;

  @Inject
  public ItemPrototypeUpdatePacketHandler(ClientEventBus clientEventBus) {
    this.clientEventBus = clientEventBus;
  }

  @Subscribe
  public void handle(ItemPrototypeUpdatePacket itemPrototypeUpdatePacket) {
    clientEventBus.post(new UpdateItemPrototypeCommand(
        itemPrototypeUpdatePacket.getId(),
        itemPrototypeUpdatePacket.getName(),
        itemPrototypeUpdatePacket.getSpriteId(),
        itemPrototypeUpdatePacket.getItemType(),
        itemPrototypeUpdatePacket.getDescription()
    ));
  }
}