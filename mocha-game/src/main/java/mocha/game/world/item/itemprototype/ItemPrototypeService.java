package mocha.game.world.item.itemprototype;

import java.util.List;

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
    return save(itemPrototype);
  }

  public ItemPrototype save(ItemPrototype itemPrototype) {
    return itemPrototypeRepository.save(itemPrototype);
  }

  public List<ItemPrototype> findAll() {
    return itemPrototypeRepository.findAll();
  }
}
