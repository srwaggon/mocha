package mocha.game.world.entity.movement.collision;

import java.util.Collections;
import java.util.Set;

import mocha.game.world.Location;

public class SimpleCollision implements Collision {

  @Override
  public boolean isColliding(Location location) {
    return getColliders(location).isEmpty();
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return Collections.emptySet();
  }
}
