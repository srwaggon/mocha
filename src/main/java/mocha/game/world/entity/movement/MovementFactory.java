package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class MovementFactory {

  @Inject
  private EventBus eventBus;

  public InputMomentumMovement newInputMomentumMovement() {
    return new InputMomentumMovement();
  }

  public MomentumMovement newMomentumMovement() {
    return new MomentumMovement();
  }

  public SimpleMovement newSimpleMovement() {
    return new SimpleMovement();
  }

  public SmoothingMovement newSmoothingMovement() {
    return new SmoothingMovement();
  }
}