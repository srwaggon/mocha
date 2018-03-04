package mocha.game.event;

import com.google.common.eventbus.EventBus;

import mocha.game.event.world.entity.AddEntityEvent;
import mocha.game.event.world.entity.EntityUpdateEvent;
import mocha.game.world.entity.Entity;
import mocha.game.event.world.entity.RemoveEntityEvent;
import mocha.game.world.entity.brain.PickUpItemEvent;

public class MochaEventbus extends EventBus {

  public void addEntity(Entity entity) {
    post(new AddEntityEvent(entity));
  }

  public void updateEntity(Entity entity) {
    post(new EntityUpdateEvent(entity));
  }

  public void removeEntity(Entity entity) {
    post(new RemoveEntityEvent(entity));
  }

  public void pickUpItem(Entity entity) {
    post(new PickUpItemEvent(entity));
  }

}
