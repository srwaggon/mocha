package mocha.game.world.collision;

import java.util.Set;

import mocha.game.world.Location;

public interface Collision {

  boolean isColliding(Location location);

  Set<Collider> getColliders(Location location);
}
