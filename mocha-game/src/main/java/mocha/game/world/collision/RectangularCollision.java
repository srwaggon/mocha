package mocha.game.world.collision;

import mocha.game.world.Location;

public class RectangularCollision extends SimpleCollision {

  private final int width;
  private final int height;
  private final Location location;

  RectangularCollision(Location location, int width, int height) {
    this.width = width;
    this.height = height;
    this.location = location;
  }

  @Override
  public boolean isColliding(Location location) {
    return isBetween(left(), location.getX(), right())
        && isBetween(top(), location.getY(), bottom());
  }

  private boolean isBetween(int low, int value, int high) {
    return low <= value && value < high;
  }

  private int left() {
    return location.getX();
  }

  private int right() {
    return location.getX() + width - 1;
  }

  private int top() {
    return location.getY();
  }

  private int bottom() {
    return location.getY() + height - 1;
  }

}
