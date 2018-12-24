package mocha.game.world.item;

import mocha.game.world.item.command.UpdateItemPrototypeCommand;
import mocha.shared.Repository;

public class ItemPrototypeService {

  private Repository<ItemPrototype, Integer> itemPrototypeRepository;

  public ItemPrototypeService(
      Repository<ItemPrototype, Integer> itemPrototypeRepository
  ) {
    this.itemPrototypeRepository = itemPrototypeRepository;
  }

  public ItemPrototype findById(int id) {
    return itemPrototypeRepository.findById(id)
        .orElse(new ItemPrototype(0, "Default", "/mocha/gfx/tiles/dirt.png::0", ItemType.CURRENCY, "Default Item"));
  }

  public ItemPrototype updateItemPrototype(UpdateItemPrototypeCommand updateItemPrototypeCommand) {
    return itemPrototypeRepository.save(new ItemPrototype(
        updateItemPrototypeCommand.getId(),
        updateItemPrototypeCommand.getName(),
        updateItemPrototypeCommand.getSpriteId(),
        updateItemPrototypeCommand.getItemType(),
        updateItemPrototypeCommand.getDescription()
    ));
  }
}
