package mocha.game.world.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.brain.RandomBrain;
import mocha.game.world.entity.movement.MovementFactory;

@Component
public class EntityFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private MovementFactory movementFactory;

  public Entity createRandomSlider() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newSlidingMovement())
        .build();
    entity.setBrain(new RandomBrain(entity));
    return entity;
  }

  public Entity createRandom() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newVelocityMovement())
        .build();
    entity.setBrain(new RandomBrain(entity));
    return entity;
  }

  public Entity createRandomAccelerating() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newAccelerationMovement())
        .build();
    entity.setBrain(new RandomBrain(entity));
    return entity;
  }
}
