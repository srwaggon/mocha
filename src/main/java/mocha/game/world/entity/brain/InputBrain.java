package mocha.game.world.entity.brain;

import mocha.game.InputKey;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;

public class InputBrain implements Brain {

  private Entity entity;

  public InputBrain(Entity entity) {
    this.entity = entity;
  }

  public void tick(long now) {
    Movement movement = entity.getMovement();
    if (InputKey.UP.isDown()) {
      movement.up();
    }

    if (InputKey.DOWN.isDown()) {
      movement.down();
    }

    if (InputKey.LEFT.isDown()) {
      movement.left();
    }

    if (InputKey.RIGHT.isDown()) {
      movement.right();
    }
  }
}
