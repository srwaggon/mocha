package mocha.game;

import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.game.world.item.UpdateItemCommand;

public interface GameLogic {

  void handle(EntityMoveCommand entityMoveCommand);

  void handle(UpdateItemCommand updateItemCommand);

  void handle(UpdateEntityPrototypeCommand updateEntityPrototypeCommand);
}
