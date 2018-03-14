package mocha.game.world.entity.brain;

import mocha.game.world.entity.Entity;

public class BrainFactory {

  public SimpleBrain newSimpleBrain() {
    return SimpleBrain.builder().build();
  }

  public RandomBrain newRandomBrain(Entity entity) {
    RandomBrain brain = new RandomBrain();
    entity.setBrain(brain);
    return brain;
  }

}
