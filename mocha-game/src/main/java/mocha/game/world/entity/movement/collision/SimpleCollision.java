package mocha.game.world.entity.movement.collision;

import mocha.game.world.Location;

public class SimpleCollision implements Collision {
  @Override
  public boolean collides(Location location) {
    return false;
  }
}
