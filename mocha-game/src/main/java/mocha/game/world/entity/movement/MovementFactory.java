package mocha.game.world.entity.movement;

import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class MovementFactory {

  private Repository<Entity, Integer> entityRepository;

  public MovementFactory(
      Repository<Entity, Integer> entityRepository
  ) {
    this.entityRepository = entityRepository;
  }

  public SlidingMovement newSlidingMovement(Entity entity) {
    return new SlidingMovement(entity.getId(), entityRepository);
  }

}