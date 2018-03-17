package mocha.game.world.entity.movement.command;

import mocha.game.Game;
import mocha.game.command.Command;
import mocha.game.world.Direction;

public class MoveCommand implements Command {
  private final Direction direction;

  MoveCommand(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public void apply(Game game) {

  }
}
