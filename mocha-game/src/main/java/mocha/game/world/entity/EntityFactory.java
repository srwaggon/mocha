package mocha.game.world.entity;

import java.util.Random;

import mocha.game.world.Location;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemService;

public class EntityFactory {

  private ItemService itemService;

  EntityFactory(
      ItemService itemService
  ) {
    this.itemService = itemService;
  }

  public Entity newRandomlyPlacedEntity(Integer id) {
    return new BaseEntity(id, new Location(random(), random()));
  }

  private int random() {
    return new Random().nextInt(16) * 32;
  }

  ItemEntity newItemEntity(Entity entity) {
    Item item = itemService.findById(entity.getTypeId());
    return new ItemEntity(entity.getId(), entity.getLocation(), item);
  }
}
