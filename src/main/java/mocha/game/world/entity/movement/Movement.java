package mocha.game.world.entity.movement;

import mocha.game.Tickable;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.Collision;

public interface Movement extends Tickable {

  Collision getCollision();

  void setCollision(Collision collision);

  Location getLocation();

  void up();

  void down();

  void left();

  void right();

  void handle(Move move);

  void setEntity(Entity entity);
}
