package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.collision.Collider;
import mocha.game.world.item.Item;
import mocha.shared.Identified;

public interface Entity extends Collider, Identified<Integer> {
  Integer getId();

  @Override
  void setId(Integer id);

  Location getLocation();

  String getSpriteId();

  void setSpriteId(String spriteId);

  double getScale();

  EntityType getEntityType();

  @Override
  boolean isBlocking();

  @Override
  void collide(Collider collider);

  int getTypeId();

  boolean isTransient();

  void setRightHand(Item item);

  void pickup(Entity entity);

  void pickedUpBy(Entity entity);

  void setScale(double scale);

}
