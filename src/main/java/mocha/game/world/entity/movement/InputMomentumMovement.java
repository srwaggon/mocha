package mocha.game.world.entity.movement;

import mocha.game.InputHandler;
import mocha.game.InputKey;

public class InputMomentumMovement extends MomentumMovement {

  private InputHandler inputHandler;

  InputMomentumMovement(InputHandler inputHandler) {
    this.inputHandler = inputHandler;
  }

  @Override
  public void tick() {
    if (inputHandler.isDown(InputKey.UP)) {
      this.up();
    }
    if (inputHandler.isDown(InputKey.DOWN)) {
      this.down();
    }
    if (inputHandler.isDown(InputKey.LEFT)) {
      this.left();
    }
    if (inputHandler.isDown(InputKey.RIGHT)) {
      this.right();
    }
    super.tick();
  }
}
