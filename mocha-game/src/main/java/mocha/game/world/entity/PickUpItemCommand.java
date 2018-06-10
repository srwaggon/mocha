package mocha.game.world.entity;

import mocha.game.command.Command;

public class PickUpItemCommand implements Command {
  private final Entity pickingUpEntity;

  public PickUpItemCommand(Entity pickingUpEntity) {
    this.pickingUpEntity = pickingUpEntity;
  }

  public Entity getPickingUpEntity() {
    return pickingUpEntity;
  }
}
