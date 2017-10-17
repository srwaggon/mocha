package mocha.game.world.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;

@Component
public class EntityFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private MovementFactory movementFactory;

  public Mob createPlayer() {
    Movement movement = movementFactory.newSlidingMovement();
    Mob mob = new Mob();
    mob.setMovementComponent(movement);
    return mob;
  }
}
