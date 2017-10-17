package mocha.game.world.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.brain.Brain;
import mocha.game.world.entity.brain.InputBrain;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;

@Component
public class EntityFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private MovementFactory movementFactory;

  public Entity createPlayer() {
    Movement movement = movementFactory.newSlidingMovement();
    Entity playerEntity = new Entity();
    Brain inputBrain = new InputBrain(playerEntity);
    playerEntity.setBrain(inputBrain);
    playerEntity.setMovement(movement);
    return playerEntity;
  }
}
