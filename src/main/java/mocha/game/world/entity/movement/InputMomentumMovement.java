package mocha.game.world.entity.movement;

import mocha.game.InputKey;

public class InputMomentumMovement extends MomentumMovement {

  @Override
  public void tick() {
    if (InputKey.UP.isDown()) {
      this.up();
    }
    if (InputKey.DOWN.isDown()) {
      this.down();
    }
    if (InputKey.LEFT.isDown()) {
      this.left();
    }
    if (InputKey.RIGHT.isDown()) {
      this.right();
    }
    super.tick();
  }
}
