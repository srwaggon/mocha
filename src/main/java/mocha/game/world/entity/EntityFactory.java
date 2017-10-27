package mocha.game.world.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.movement.MovementFactory;

@Component
public class EntityFactory {

  @Inject
  private BrainFactory brainFactory;

  @Inject
  private EventBus eventBus;

  @Inject
  private MovementFactory movementFactory;

  public Entity createRandomSlider() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newSlidingMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Entity createRandom() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newVelocityMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Entity createRandomAccelerating() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newAccelerationMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }
}
