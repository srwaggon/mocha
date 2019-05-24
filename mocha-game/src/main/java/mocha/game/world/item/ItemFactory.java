package mocha.game.world.item;

import mocha.game.world.item.itemprototype.ItemPrototypeService;

class ItemFactory {


  private ItemPrototypeService itemPrototypeService;

  ItemFactory(ItemPrototypeService itemPrototypeService) {
    this.itemPrototypeService = itemPrototypeService;
  }

  Item newDefaultItem(int id) {
    return new Item(id, itemPrototypeService.findById(0), 0, 0, 0);
  }

  Item newItemFromUpdate(UpdateItemCommand updateItemCommand) {
    return new Item(updateItemCommand.getId(), updateItemCommand.getItemPrototype(), updateItemCommand.getData0(), updateItemCommand.getData1(), updateItemCommand.getData2());
  }
}
