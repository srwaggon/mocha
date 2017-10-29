package mocha.game.world.entity.brain;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.Entity;

@Component
public class BrainFactory {

  @Inject
  private EventBus eventBus;

  public SimpleBrain newSimpleBrain() {
    return new SimpleBrain();
  }

  public RandomBrain newRandomBrain(Entity entity) {
    return RandomBrain.builder()
        .entity(entity)
        .build();
  }

  public InputBrain newInputBrain(Entity entity) {
    return InputBrain.builder()
        .entity(entity)
        .eventBus(eventBus)
        .build();
  }
}
