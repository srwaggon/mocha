package mocha.game.world.item;

import mocha.game.world.item.command.UpdateItemCommand;
import mocha.shared.Repository;

public class ItemService {

  private Repository<Item, Integer> itemRepository;
  private ItemFactory itemFactory;

  ItemService(
      Repository<Item, Integer> itemRepository,
      ItemFactory itemFactory
  ) {
    this.itemRepository = itemRepository;
    this.itemFactory = itemFactory;
  }

  public void save(Item item) {
    itemRepository.save(item);
  }

  public Item findById(int id) {
    return itemRepository.findById(id)
        .orElse(itemFactory.newDefaultItem(id));
  }

  public Item updateItem(UpdateItemCommand updateItemCommand) {
    Item item = itemRepository.findById(updateItemCommand.getId())
        .orElse(itemFactory.newItemFromUpdate(updateItemCommand));
    item.setItemPrototype(updateItemCommand.getItemPrototype());
    item.setData0(updateItemCommand.getData0());
    item.setData1(updateItemCommand.getData1());
    item.setData2(updateItemCommand.getData2());
    return itemRepository.save(item);
  }

}
