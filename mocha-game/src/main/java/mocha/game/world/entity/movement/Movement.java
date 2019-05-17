package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.shared.Identified;

public interface Movement extends Identified<Integer> {

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

  void move(Entity entity);
}
