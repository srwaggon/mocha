package mocha.game.world.entity.command;

import mocha.game.command.Command;
import mocha.game.world.entity.Entity;

public class PickUpItemCommand implements Command {

  private Entity pickingUpEntity;

  public PickUpItemCommand(Entity pickingUpEntity) {
    this.pickingUpEntity = pickingUpEntity;
  }

  public Entity getPickingUpEntity() {
    return pickingUpEntity;
  }
}
