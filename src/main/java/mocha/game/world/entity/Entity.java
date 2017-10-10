package mocha.game.world.entity;

import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;

public class Entity {
  private static int lastId = 0;
  private int id;
  Movement movementComponent = new SimpleMovement();

  Entity() {
    this.id = ++lastId;
  }

  Entity(int id) {
    this.id = id;
    lastId = id > lastId ? id : lastId;
  }

  public int getId() {
    return id;
  }

  public void tick() {
    move();
  }

  private void move() {
    this.getMovementComponent().tick();
  }

  public Movement getMovementComponent() {
    return movementComponent;
  }

  public int getSpriteId() {
    return 2;
  }

  public double getScale() {
    return 1.0;
  }

}
