package mocha.game;

import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.item.UpdateItemCommand;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;

public interface GameLogic {

  void handle(EntityMoveCommand entityMoveCommand);

  void handle(UpdateItemPrototypeCommand updateItemPrototypeCommand);

  void handle(UpdateItemCommand updateItemCommand);
}
