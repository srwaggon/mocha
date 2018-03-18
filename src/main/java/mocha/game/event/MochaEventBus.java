package mocha.game.event;

import com.google.common.eventbus.EventBus;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.PickUpItemCommand;
import mocha.game.world.entity.command.RemoveEntityCommand;
import mocha.game.world.entity.event.AddEntityEvent;
import mocha.game.world.entity.event.EntityUpdateEvent;
import mocha.game.world.entity.event.RemoveEntityEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.event.EntityMovementEvent;

public class MochaEventBus extends EventBus {

  public void addEntity(Entity entity) {
    post(new AddEntityEvent(entity));
  }

  public void updateEntity(Entity entity) {
    post(new EntityUpdateEvent(entity));
  }

  public void moveEvent(Movement movement) {
    post(new EntityMovementEvent(movement));
  }

  public void removeEntityCommand(Entity entity) {
    post(new RemoveEntityCommand(entity));
  }

  public void removeEntityEvent(Entity entity) {
    post(new RemoveEntityEvent(entity));
  }

  public void pickUpItemCommand(Entity entity) {
    post(new PickUpItemCommand(entity));
  }

}
