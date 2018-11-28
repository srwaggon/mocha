package mocha.game;

import mocha.game.world.entity.movement.command.EntityMoveCommand;

public interface GameLogic {

  void handle(EntityMoveCommand entityMoveCommand);
}
