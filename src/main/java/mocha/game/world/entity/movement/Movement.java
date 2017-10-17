package mocha.game.world.entity.movement;

import mocha.game.world.Location;

public interface Movement {

  Location getLocation();

  void setLocation(Location location);

  void up();

  void down();

  void left();

  void right();

  default void tick() {
  }

}
