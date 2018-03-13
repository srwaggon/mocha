package mocha.game.world.entity.movement;

import mocha.game.world.Direction;

public class Move {
  private final Direction direction;

  Move(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }
}
