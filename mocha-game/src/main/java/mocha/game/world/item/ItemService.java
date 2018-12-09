package mocha.game.world.item;

import mocha.shared.Repository;

public class ItemService {

  private Repository<Item, Integer> itemRepository;
  private ItemPrototypeService itemPrototypeService;

  public ItemService(
      Repository<Item, Integer> itemRepository,
      ItemPrototypeService itemPrototypeService
  ) {
    this.itemRepository = itemRepository;
    this.itemPrototypeService = itemPrototypeService;
  }

  public void addItem(Item item) {
    itemRepository.save(item);
  }

  public Item findById(int id) {
    return itemRepository.findById(id).orElse(defaultItem(id));
  }

  private Item defaultItem(int id) {
    return new Item(id, itemPrototypeService.findById(0), 0, 0, 0);
  }
}
