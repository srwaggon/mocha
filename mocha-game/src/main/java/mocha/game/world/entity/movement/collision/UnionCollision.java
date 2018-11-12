package mocha.game.world.entity.movement.collision;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mocha.game.world.Location;

public class UnionCollision extends SimpleCollision {

  private Collision[] collisions;

  UnionCollision(Collision... collisions) {
    this.collisions = collisions;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    Set<Collider> set = new HashSet<>();
    Arrays.stream(collisions)
        .map(collision -> collision.getColliders(location))
        .forEach(set::addAll);
    return set;
  }
}
