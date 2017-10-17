package mocha.game.world.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.brain.InputBrain;
import mocha.game.world.entity.brain.RandomBrain;
import mocha.game.world.entity.movement.MovementFactory;

@Component
public class EntityFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private MovementFactory movementFactory;

  public Entity createInputSlider() {
    Entity playerEntity = new Entity();
    playerEntity.setBrain(new InputBrain(playerEntity));
    playerEntity.setMovement(movementFactory.newSlidingMovement());
    return playerEntity;
  }

  public Entity createInputEntity() {
    Entity playerEntity = new Entity();
    playerEntity.setBrain(new InputBrain(playerEntity));
    playerEntity.setMovement(movementFactory.newVelocityMovement());
    return playerEntity;
  }

  public Entity createRandomSlider() {
    Entity entity = new Entity();
    entity.setBrain(new RandomBrain(entity));
    entity.setMovement(movementFactory.newSlidingMovement());
    return entity;
  }

  public Entity createRandom() {
    Entity entity = new Entity();
    entity.setBrain(new RandomBrain(entity));
    entity.setMovement(movementFactory.newVelocityMovement());
    return entity;
  }
}
