package mocha.game.world.entity.movement;

import mocha.game.Tickable;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public interface Movement extends Tickable {

  Collision getCollision();

  void setCollision(Collision collision);

  Location getLocation();

  int getXOffset();

  int getYOffset();

  Direction getDirection();

  Entity getEntity();

  void setEntity(Entity entity);

  void up();

  void down();

  void left();

  void right();

  void handle(EntityMoveCommand moveCommand);
}
