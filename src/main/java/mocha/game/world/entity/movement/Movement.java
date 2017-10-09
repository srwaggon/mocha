package mocha.game.world.entity.movement;

import mocha.game.world.Location;

public interface Movement {

  Location getLocation();

  void setLocation(Location location);

  default void tick() {
  }

}
