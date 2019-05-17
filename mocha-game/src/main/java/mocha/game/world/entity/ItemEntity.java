package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.item.Item;

import static mocha.game.world.entity.EntityType.ITEM;

public class ItemEntity extends BaseEntity {

  private Item item;

  public ItemEntity() {
  }

  public ItemEntity(Integer id, Item item) {
    this(id, Location.at(0, 0), item);
  }

  public ItemEntity(Integer id, Location location, Item item) {
    super(id, location);
    this.item = item;
    this.setEntityType(ITEM);
    this.setBlocking(false);
  }

  @Override
  public String getSpriteId() {
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

  @Override
  public void pickedUpBy(Entity entity) {
    entity.setRightHand(item);
  }
}
