package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class SimpleMovement implements Movement {

  @Override
  public Integer getId() {
    return null;
  }

  @Override
  public void setId(Integer id) {

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
    return 0;
  }

  public int getYOffset() {
    return 0;
  }

  @Override
  public Direction getDirection() {
    return null;
  }

  @Override
  public Collision getCollision() {
    return null;
  }

  @Override
  public void setCollision(Collision collision) {

  }

}
