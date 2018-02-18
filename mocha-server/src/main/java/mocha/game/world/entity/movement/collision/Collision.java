package mocha.game.world.entity.movement.collision;

import mocha.game.world.Location;

public interface Collision {

  boolean collides(Location location);

}
