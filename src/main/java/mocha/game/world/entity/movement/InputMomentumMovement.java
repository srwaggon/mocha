package mocha.game.world.entity.movement;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.InputHandler;
import mocha.game.InputKey;
import mocha.game.world.Location;

@Component
public class InputMomentumMovement implements Movement {

  @Inject
  private MomentumMovement momentumMovement;
  @Inject
  private InputHandler input;

  @Override
  public void tick() {
    if (input.isDown(InputKey.UP)) {
      momentumMovement.up();
    }
    if (input.isDown(InputKey.DOWN)) {
      momentumMovement.down();
    }
    if (input.isDown(InputKey.LEFT)) {
      momentumMovement.left();
    }
    if (input.isDown(InputKey.RIGHT)) {
      momentumMovement.right();
    }
    momentumMovement.tick();
  }

  public void setLocation(Location location) {
    momentumMovement.setLocation(location);
  }

  public void setSpeed(double speed) {
    momentumMovement.setSpeed(speed);
  }
}
