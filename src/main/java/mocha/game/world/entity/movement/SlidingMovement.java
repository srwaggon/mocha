package mocha.game.world.entity.movement;

import com.google.common.base.Preconditions;

import mocha.game.InputKey;
import mocha.game.world.Direction;
import mocha.game.world.Location;

public class SlidingMovement extends SimpleMovement {

  private double distance;
  private double duration;
  private int timeStep;
  private Location startLocation;
  private double endLocation;
  private Direction direction;

  public SlidingMovement(double distance, int duration) {
    this.distance = distance;
    this.duration = duration;
    Preconditions.checkArgument(duration > 0, "Duration must be greater than 0.");
    timeStep = duration;
  }

  @Override
  public void tick() {
    startMoving();

    move();
  }

  private void startMoving() {
    if (timeStep != duration) {
      return;
    }

    timeStep = 0;
    setStartLocation();

    if (InputKey.RIGHT.isDown()) {
      endLocation = startLocation.getX() + distance;
      direction = Direction.EAST;
    }

    if (InputKey.DOWN.isDown()) {
      endLocation = startLocation.getY() + distance;
      direction = Direction.SOUTH;
    }
  }

  private void setStartLocation() {
    startLocation = new Location();
    startLocation.setX(location.getX());
    startLocation.setY(location.getY());
  }

  private void move() {
    if (timeStep < duration) {
      timeStep++;
      double timeDelta = timeStep / duration;

      if (Direction.EAST.equals(direction)) {
        double xDelta = endLocation - startLocation.getX();
        double currentX = startLocation.getX() + xDelta * timeDelta;
        this.getLocation().setX(currentX);
      }

      if (Direction.SOUTH.equals(direction)) {
        double yDelta = endLocation - startLocation.getY();
        double currentY = startLocation.getY() + yDelta * timeDelta;
        this.getLocation().setY(currentY);
      }
    }
  }

}
