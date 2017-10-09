package mocha.game.world.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.movement.MovementFactory;

@Component
public class EntityFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private MovementFactory movementFactory;

  public PlayerMob createPlayer() {
    return new PlayerMob(movementFactory.newInputMomentumMovement());
  }
}
