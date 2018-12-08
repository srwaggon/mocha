package mocha.game.world.item;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityType;

public class ItemEntity extends Entity {

  private Item item;

  public ItemEntity() {
  }

  public ItemEntity(Integer id, Location location, Item item) {
    super(id, location);
    this.item = item;
    this.setEntityType(EntityType.ITEM);
    this.setBlocking(false);
  }

  @Override
  public int getSpriteId() {
    return item.getSpriteId();
  }

  public Item getItem() {
    return item;
  }

  @Override
  public int getTypeId() {
    return item.getId();
  }

  @Override
  public boolean isTransient() {
    return true;
  }
}
