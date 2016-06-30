package mocha.game.world.entity.movement;

import org.springframework.stereotype.Component;

import mocha.game.world.Location;

@Component
public class MomentumMovement implements Movement {
  private double speed;
  private Location location;
  private double dx;
  private double dy;

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setDx(double dx) {
    this.dx = dx;
  }

  public double getDx() {
    return dx;
  }

  public void setDy(double dy) {
    this.dy = dy;
  }

  public double getDy() {
    return dy;
  }

  public void applyDx() {
    location.setX(location.getX() + getDx());
  }

  public void applyDy() {
    location.setY(location.getY() + getDy());
  }

  public void up() {
    setDy(-getSpeed());
  }

  public void down() {
    setDy(getSpeed());
  }

  public void left() {
    setDx(-getSpeed());
  }

  public void right() {
    setDx(getSpeed());
  }

  public void tick() {
    applyDx();
    this.dx = 0.0;
    applyDy();
    this.dy = 0.0;
  }
}
