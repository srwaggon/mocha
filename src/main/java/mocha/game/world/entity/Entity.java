package mocha.game.world.entity;

import lombok.Data;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;

@Data
public class Entity {
  private static int lastId = 0;
  private final int id;
  private Movement movementComponent = new SimpleMovement();

  Entity() {
    this.id = ++lastId;
  }

  Entity(int id) {
    this.id = id;
    lastId = id > lastId ? id : lastId;
  }

  public void tick() {
    move();
  }

  private void move() {
    this.getMovementComponent().tick();
  }

  public int getSpriteId() {
    return 2;
  }

  public double getScale() {
    return 1.0;
  }

}
