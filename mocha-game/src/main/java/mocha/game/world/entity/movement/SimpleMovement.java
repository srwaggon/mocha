package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class SimpleMovement implements Movement {

  protected Collision collision;
  protected Direction direction;
  int xOffset = 0;
  int yOffset = 0;

  SimpleMovement(Collision collision) {
    this.collision = collision;
  }

  @Override
  public Integer getId() {
    return null;
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
  public boolean isMoving() {
    return false;
  }

  @Override
  public Location getLocation() {
    return null;
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

}
