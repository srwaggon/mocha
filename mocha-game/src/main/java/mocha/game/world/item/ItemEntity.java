package mocha.game.world.item;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;

public class ItemEntity extends Entity {

  private Item item;

  public ItemEntity() {
  }

  public ItemEntity(Item item) {
    this.item = item;
  }

  public ItemEntity(Item item, Integer id, Location location) {
    super(id, location);
  }

  @Override
  public int getSpriteId() {
    return item.getSpriteId();
  }

  @Override
  public boolean isBlocking() {
    return false;
  }

  public Item getItem() {
    return item;
  }
}
