package mocha.game.world.entity.movement;

import lombok.Data;

@Data
public class VelocityMovement extends SimpleMovement {

  private double speed = 4.0;
  private double xVelocity;
  private double yVelocity;

  public void applyXVelocity() {
    location.addX(getXVelocity());
  }

  public void applyDy() {
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

  @Override
  public void tick(long now) {
    applyXVelocity();
    applyDy();
    setXVelocity(0.0);
    setYVelocity(0.0);
  }
}
