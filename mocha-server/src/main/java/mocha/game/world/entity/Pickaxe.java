package mocha.game.world.entity;

import mocha.game.world.entity.brain.Brain;
import mocha.game.world.entity.movement.Movement;

public class Pickaxe extends Entity {
  Pickaxe(Movement movement, Brain brain) {
    super();
    this.setMovement(movement);
    this.setBrain(brain);
  }

  @Override
  public int getSpriteId() {
    return 100;
  }
}
