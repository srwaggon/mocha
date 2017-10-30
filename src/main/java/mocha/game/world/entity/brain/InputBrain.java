package mocha.game.world.entity.brain;

import com.google.common.eventbus.EventBus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.InputKey;
import mocha.game.world.entity.Entity;

@Builder
@AllArgsConstructor
public class InputBrain implements Brain {

  private Entity entity;
  private EventBus eventBus;

  public void tick(long now) {
    move();
    pickUpItems();
  }

  private void move() {
    if (InputKey.UP.isDown()) {
      entity.getMovement().up();
    }
    if (InputKey.DOWN.isDown()) {
      entity.getMovement().down();
    }
    if (InputKey.LEFT.isDown()) {
      entity.getMovement().left();
    }
    if (InputKey.RIGHT.isDown()) {
      entity.getMovement().right();
    }
  }

  private void pickUpItems() {
    if (InputKey.PICKUP.isClicked()) {
      eventBus.post(new PickUpItemEvent(entity));
    }
  }

}
