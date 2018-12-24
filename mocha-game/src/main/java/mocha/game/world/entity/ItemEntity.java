package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.item.Item;

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
  protected void pickedUpBy(Entity entity) {
    entity.setRightHand(item);
  }
}
