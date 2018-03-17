package mocha.game.world.entity.movement;

import mocha.game.world.Direction;

public class MoveCommand {
  private final Direction direction;

  MoveCommand(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }
}
