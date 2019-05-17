package mocha.game.world.entity;

import java.util.Random;

import mocha.game.world.Location;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.item.Item;
import mocha.shared.IdFactory;

public class EntityFactory {

  private IdFactory<Entity> entityIdFactory;

  EntityFactory(
      IdFactory<Entity> entityIdFactory
  ) {
    this.entityIdFactory = entityIdFactory;
  }

  public Entity newEntity(EntityPrototype entityPrototype) {
    return new BaseEntity(newId(), entityPrototype);
  }

  private Integer newId() {
    return entityIdFactory.newId();
  }

  public Entity newRandomlyPlacedEntity() {
    return new BaseEntity(newId(), new Location(random(), random()));
  }

  private int random() {
    return new Random().nextInt(16) * 32;
  }

  public ItemEntity newItemEntity(Item item) {
    return new ItemEntity(newId(), Location.at(0, 0), item);
  }

}
