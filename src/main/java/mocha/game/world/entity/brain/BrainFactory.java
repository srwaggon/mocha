package mocha.game.world.entity.brain;

public class BrainFactory {

  public SimpleBrain newSimpleBrain() {
    return SimpleBrain.builder().build();
  }

  public RandomBrain newRandomBrain() {
    return new RandomBrain();
  }

}
