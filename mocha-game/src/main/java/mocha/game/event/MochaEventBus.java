package mocha.game.event;

import com.google.common.eventbus.EventBus;

import mocha.game.Player;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.PickUpItemCommand;
import mocha.game.world.entity.command.RemoveEntityCommand;
import mocha.game.world.entity.event.EntityAddedEvent;
import mocha.game.world.entity.event.EntityRemovedEvent;
import mocha.game.world.entity.event.EntityUpdatedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.movement.event.EntityMovementEvent;
import mocha.shared.task.event.TaskEvent;

public class MochaEventBus extends EventBus {

  public void postPlayerAddedEvent(Player player) {
    post(new PlayerAddedEvent(player));
  }

  public void postPlayerRemovedEvent(Player player) {
    post(new PlayerRemovedEvent(player));
  }

  public void postEntityAddedEvent(Entity entity) {
    post(new EntityAddedEvent(entity));
  }

  public void postEntityUpdatedEvent(Entity entity) {
    post(new EntityUpdatedEvent(entity));
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

  public void postEntityRemovedEvent(Entity entity) {
    post(new EntityRemovedEvent(entity));
  }

  public void postPickUpItemCommand(Entity entity) {
    post(new PickUpItemCommand(entity));
  }

  public void postTaskEvent(Runnable runnable) {
    post(new TaskEvent(runnable));
  }
}
