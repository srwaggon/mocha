package mocha.game.world.entity.movement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mocha.game.world.Location;

@Data
@EqualsAndHashCode(callSuper = true)
public class VelocityMovement extends SimpleMovement {

  private double speed = 2.0;
  private double xVelocity;
  private double yVelocity;
  private int width;
  private int height;

  private void applyXVelocity() {
    location.addX(getXVelocity());
  }

  private void applyYVelocity() {
    location.addY(getYVelocity());
  }

  public void up() {
    setYVelocity(-getSpeed());
  }

  public void down() {
    setYVelocity(getSpeed());
  }

  public void left() {
    setXVelocity(-getSpeed());
  }

  public void right() {
    setXVelocity(getSpeed());
  }

  public Location topLeft() {
    Location location = this.getLocation();
    double y = location.getY();
    double x = location.getX();
    return new Location(x, y);
  }

  public Location topRight() {
    Location location = this.getLocation();
    double x = location.getX() + this.getWidth();
    double y = location.getY();
    return new Location(x, y);
  }

  public Location bottomLeft() {
    Location location = this.getLocation();
    double x = location.getX();
    double y = location.getY() + this.getHeight();
    return new Location(x, y);
  }

  public Location bottomRight() {
    Location location = this.getLocation();
    double x = location.getX() + this.getWidth();
    double y = location.getY() + this.getHeight();
    return new Location(x, y);
  }

  @Override
  public void tick(long now) {
    applyXVelocity();
    applyYVelocity();
    setXVelocity(0.0);
    setYVelocity(0.0);
  }
}
