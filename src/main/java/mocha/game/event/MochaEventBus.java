package mocha.game.event;

import com.google.common.eventbus.EventBus;

import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.PickUpItemCommand;
import mocha.game.world.entity.command.RemoveEntityCommand;
import mocha.game.world.entity.event.AddEntityEvent;
import mocha.game.world.entity.event.EntityUpdateEvent;
import mocha.game.world.entity.event.RemoveEntityEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.shared.task.event.TaskEvent;

public class MochaEventBus extends EventBus {

  public void postAddEntityEvent(Entity entity) {
    post(new AddEntityEvent(entity));
  }

  public void postEntityUpdateEvent(Entity entity) {
    post(new EntityUpdateEvent(entity));
  }

  public void postMoveCommand(Entity entity) {
    EntityMoveCommand moveCommand = EntityMoveCommand.builder()
        .entityId(entity.getId())
        .direction(Direction.random())
        .xOffset(entity.getMovement().getXOffset())
        .yOffset(entity.getMovement().getYOffset())
        .build();
    postMoveCommand(moveCommand);
  }

  public void postMoveCommand(EntityMoveCommand entityMoveCommand) {
    post(entityMoveCommand);
  }

  public void postMoveEvent(Movement movement) {
    post(new EntityMovementEvent(movement));
  }

  public void postRemoveEntityCommand(Entity entity) {
    post(new RemoveEntityCommand(entity));
  }

  public void postRemoveEntityEvent(Entity entity) {
    post(new RemoveEntityEvent(entity));
  }

  public void postPickUpItemCommand(Entity entity) {
    post(new PickUpItemCommand(entity));
  }

  public void postTaskEvent(Runnable runnable) {
    post(new TaskEvent(runnable));
  }
}
