package mocha.game.world.entity.movement;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.CollisionFactory;

public class MovementFactory {

  private CollisionFactory collisionFactory;

  public MovementFactory(CollisionFactory collisionFactory) {
    this.collisionFactory = collisionFactory;
  }

  public SimpleMovement newSimpleMovement() {
    return SimpleMovement.builder()
        .collision(collisionFactory.newSimpleCollision())
        .build();
  }

  public SlidingMovement newSlidingMovement(Entity entity) {
    return new SlidingMovement(collisionFactory.newEntityHitBoxCollision(entity, 32, 32));
  }
}