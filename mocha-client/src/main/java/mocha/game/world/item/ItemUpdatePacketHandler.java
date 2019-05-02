package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.net.packet.PacketHandler;

@Component
public class ItemUpdatePacketHandler implements PacketHandler {

  private ItemPrototypeService itemPrototypeService;
  private ItemService itemService;

  @Inject
  public ItemUpdatePacketHandler(
      ItemPrototypeService itemPrototypeService,
      ItemService itemService
  ) {
    this.itemPrototypeService = itemPrototypeService;
    this.itemService = itemService;
  }

  @Subscribe
  public void handle(ItemUpdatePacket itemUpdatePacket) {
    ItemPrototype itemPrototype = itemPrototypeService.findById(itemUpdatePacket.getItemPrototypeId());
    Item item = new Item(itemUpdatePacket.getId(), itemPrototype, itemUpdatePacket.getData0(), itemUpdatePacket.getData1(), itemUpdatePacket.getData2());
    itemService.save(item);
  }
}