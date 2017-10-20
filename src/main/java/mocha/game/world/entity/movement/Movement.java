package mocha.game.world.entity.movement;

import mocha.game.Tickable;
import mocha.game.world.Location;

public interface Movement extends Tickable {

  Location getLocation();

  void setLocation(Location location);

  void up();

  void down();

  void left();

  void right();

}
