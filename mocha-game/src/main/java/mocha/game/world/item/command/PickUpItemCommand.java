package mocha.game.world.item.command;

import mocha.game.command.Command;
import mocha.game.world.entity.Entity;

public class PickUpItemCommand implements Command {
  private final Entity pickingUpEntity;

  public PickUpItemCommand(Entity pickingUpEntity) {
    this.pickingUpEntity = pickingUpEntity;
  }

  public Entity getPickingUpEntity() {
    return pickingUpEntity;
  }
}
