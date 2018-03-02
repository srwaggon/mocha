package mocha.game;

import com.google.common.eventbus.EventBus;

import mocha.game.world.entity.AddEntityEvent;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.RemoveEntityEvent;
import mocha.game.world.entity.brain.PickUpItemEvent;

public class MochaEventbus extends EventBus {

  public void addEntity(Entity entity) {
    post(new AddEntityEvent(entity));
  }

  public void removeEntity(Entity entity) {
    post(new RemoveEntityEvent(entity));
  }

  public void pickUpItem(Entity entity) {
    post(new PickUpItemEvent(entity));
  }

}
