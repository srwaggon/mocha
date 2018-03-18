package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.collision.SimpleCollision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class SimpleMovement implements Movement {

  protected Entity entity;
  protected Collision collision;
  protected Direction direction;
  int xOffset = 0;
  int yOffset = 0;

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
  public void handle(EntityMoveCommand moveCommand) {

  }

  @Override
  public void tick(long now) {

  }

  @Override
  public Entity getEntity() {
    return entity;
  }

  @Override
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  @Override
  public Location getLocation() {
    return entity.getLocation();
  }

  public int getXOffset() {
    return xOffset;
  }

  public int getYOffset() {
    return yOffset;
  }

  @Override
  public Direction getDirection() {
    return direction;
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
