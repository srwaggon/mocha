package mocha.game.world.entity.movement;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.CollisionFactory;

public class MovementFactory {

  private CollisionFactory collisionFactory;

  public MovementFactory(CollisionFactory collisionFactory) {
    this.collisionFactory = collisionFactory;
  }


  public SimpleMovement newSimpleMovement() {
    return SimpleMovement.builder()
        .location(new Location())
        .collision(collisionFactory.newSimpleCollision())
        .build();
  }
}