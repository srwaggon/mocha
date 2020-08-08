package mocha.game.world.entity.movement.event;

import mocha.game.event.MochaEvent;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class EntityMovementEvent implements MochaEvent {
  private EntityMoveCommand entityMoveCommand;

  public EntityMovementEvent(EntityMoveCommand entityMoveCommand) {
    this.entityMoveCommand = entityMoveCommand;
  }

  public EntityMoveCommand getEntityMoveCommand() {
    return entityMoveCommand;
  }
}
