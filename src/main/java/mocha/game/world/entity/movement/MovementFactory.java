package mocha.game.world.entity.movement;

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

  public SlidingMovement newSlidingMovement() {
    return new SlidingMovement(collisionFactory.newHitBoxCollision());
  }
}