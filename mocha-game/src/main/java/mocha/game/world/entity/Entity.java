package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.collision.Collider;
import mocha.game.world.collision.Collision;
import mocha.game.world.entity.movement.MovementType;
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

  MovementType getMovementType();

  void setMovementType(MovementType movementType);

  int getSpeed();

  void setSpeed(int speed);

  int getWidth();

  void setWidth(int width);

  int getHeight();

  void setHeight(int height);

  void setCollision(Collision collision);

  Collision getCollision();

}
