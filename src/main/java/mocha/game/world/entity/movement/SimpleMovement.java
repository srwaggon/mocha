package mocha.game.world.entity.movement;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.collision.SimpleCollision;

public class SimpleMovement implements Movement {

  protected Collision collision;
  private Entity entity;

  public static SimpleMovementBuilder builder() {
    return new SimpleMovementBuilder();
  }

  public SimpleMovement(Collision collision) {
    this.collision = collision;
  }

  @Override
  public void up() {

  }

  @Override
  public void down() {

  }

  @Override
  public void left() {

  }

  @Override
  public void right() {

  }

  @Override
  public void handle(Move move) {

  }

  @Override
  public void tick(long now) {

  }

  @Override
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  @Override
  public Location getLocation() {
    return entity.getLocation();
  }

  @Override
  public Collision getCollision() {
    return collision;
  }

  @Override
  public void setCollision(Collision collision) {
    this.collision = collision;
  }

  static class SimpleMovementBuilder {

    protected Location location = new Location();
    protected Collision collision = new SimpleCollision();

    public SimpleMovementBuilder collision(Collision collision) {
      this.collision = collision;
      return this;
    }

    public SimpleMovement build() {
      return new SimpleMovement(collision);
    }
  }
}
