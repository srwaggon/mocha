package mocha.game.world.entity.brain;

import com.google.common.eventbus.EventBus;

import mocha.game.world.entity.Entity;

public class BrainFactory {

  private EventBus eventBus;

  public BrainFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public SimpleBrain newSimpleBrain() {
    return new SimpleBrain();
  }

  public RandomBrain newRandomBrain(Entity entity) {
    return RandomBrain.builder()
        .entity(entity)
        .build();
  }

}
