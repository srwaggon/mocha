package mocha.game.world.entity.movement;

import mocha.game.world.collision.CollisionFactory;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class MovementFactory {

  private CollisionFactory collisionFactory;
  private Repository<Entity, Integer> entityRepository;

  public MovementFactory(
      CollisionFactory collisionFactory,
      Repository<Entity, Integer> entityRepository
  ) {
    this.collisionFactory = collisionFactory;
    this.entityRepository = entityRepository;
  }

  public SlidingMovement newSlidingMovement(Entity entity) {
    return new SlidingMovement(entity.getId(), collisionFactory.newEntityHitBoxCollision(entity, 32, 32), entityRepository);
  }
}