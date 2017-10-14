package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.InputHandler;

@Component
public class MovementFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private InputHandler inputHandler;

  public InputMomentumMovement newInputMomentumMovement() {
    return new InputMomentumMovement(inputHandler);
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