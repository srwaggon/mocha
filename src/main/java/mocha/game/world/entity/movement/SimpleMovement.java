package mocha.game.world.entity.movement;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.collision.SimpleCollision;

public class SimpleMovement implements Movement {

  protected Location location;
  protected Collision collision;

  public static SimpleMovementBuilder builder() {
    return new SimpleMovementBuilder();
  }

  public SimpleMovement(Location location, Collision collision) {
    this.location = location;
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
  public Location getLocation() {
    return location;
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
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

    public SimpleMovementBuilder location(Location location) {
      this.location = location;
      return this;
    }

    public SimpleMovementBuilder collision(Collision collision) {
      this.collision = collision;
      return this;
    }

    public SimpleMovement build() {
      return new SimpleMovement(location, collision);
    }
  }
}
