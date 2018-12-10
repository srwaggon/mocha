package mocha.game.world.entity.movement;

import mocha.game.Tickable;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.collision.Collision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.shared.Identified;

public interface Movement extends Tickable, Identified<Integer> {

  Collision getCollision();

  void setCollision(Collision collision);

  Location getLocation();

  int getXOffset();

  int getYOffset();

  Direction getDirection();

  boolean isMoving();

  void up();

  void down();

  void left();

  void right();

  void handle(EntityMoveCommand moveCommand);
}
