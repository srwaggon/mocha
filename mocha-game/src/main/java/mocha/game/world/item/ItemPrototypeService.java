package mocha.game.world.item;

import mocha.game.world.item.command.UpdateItemPrototypeCommand;
import mocha.shared.Repository;

public class ItemPrototypeService {

  private Repository<ItemPrototype, Integer> itemPrototypeRepository;
  private ItemPrototypeFactory itemPrototypeFactory;

  public ItemPrototypeService(
      Repository<ItemPrototype, Integer> itemPrototypeRepository,
      ItemPrototypeFactory itemPrototypeFactory
  ) {
    this.itemPrototypeRepository = itemPrototypeRepository;
    this.itemPrototypeFactory = itemPrototypeFactory;
  }

  public ItemPrototype findById(int id) {
    return itemPrototypeRepository.findById(id)
        .orElse(itemPrototypeFactory.newDefaultItemPrototype());
  }

  public ItemPrototype updateItemPrototype(UpdateItemPrototypeCommand updateItemPrototypeCommand) {
    ItemPrototype itemPrototype = itemPrototypeRepository.findById(updateItemPrototypeCommand.getId())
        .orElse(itemPrototypeFactory.newItemPrototype(updateItemPrototypeCommand));
    itemPrototype.setSpriteId(updateItemPrototypeCommand.getSpriteId());
    itemPrototype.setDescription(updateItemPrototypeCommand.getDescription());
    itemPrototype.setItemType(updateItemPrototypeCommand.getItemType());
    itemPrototype.setName(updateItemPrototypeCommand.getName());
    return itemPrototypeRepository.save(itemPrototype);
  }

}
