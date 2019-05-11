package mocha.game.world.entity.movement;

import com.google.common.collect.Maps;

import java.util.Map;

import mocha.game.world.Direction;
import mocha.game.world.collision.Collision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class BaseMovement extends SimpleMovement {

  private final Map<Direction, Runnable> moveMap = Maps.newConcurrentMap();
  private int id;
  Collision collision;
  Direction direction;
  int xOffset = 0;
  int yOffset = 0;

  BaseMovement(int id, Collision collision) {
    this.id = id;
    this.collision = collision;

    moveMap.put(Direction.NORTH, this::up);
    moveMap.put(Direction.EAST, this::right);
    moveMap.put(Direction.SOUTH, this::down);
    moveMap.put(Direction.WEST, this::left);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public void handle(EntityMoveCommand moveCommand) {
    if (isStartingMove(moveCommand)) {
      moveMap.get(moveCommand.getDirection()).run();
    }

    if (isStoppingX(moveCommand)) {
      this.xOffset = 0;
    }

    if (isStoppingY(moveCommand)) {
      this.yOffset = 0;
    }

  }

  private boolean isStoppingY(EntityMoveCommand moveCommand) {
    return moveCommand.getDirection().getYMultiplier() != 0 && moveCommand.getYOffset() == 0;
  }

  private boolean isStoppingX(EntityMoveCommand moveCommand) {
    return moveCommand.getDirection().getXMultiplier() != 0 && moveCommand.getXOffset() == 0;
  }

  private boolean isStartingMove(EntityMoveCommand moveCommand) {
    return startingXMove(moveCommand) || startingYMove(moveCommand);
  }

  private boolean startingXMove(EntityMoveCommand moveCommand) {
    return moveCommand.getDirection().getXMultiplier() == moveCommand.getXOffset();
  }

  private boolean startingYMove(EntityMoveCommand moveCommand) {
    return moveCommand.getDirection().getYMultiplier() == moveCommand.getYOffset();
  }

  @Override
  public boolean isMoving() {
    return xOffset != 0 || yOffset != 0;
  }

  @Override
  public int getXOffset() {
    return xOffset;
  }

  @Override
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

