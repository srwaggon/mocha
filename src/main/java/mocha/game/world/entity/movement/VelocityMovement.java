package mocha.game.world.entity.movement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VelocityMovement extends SimpleMovement {

  private double speed = 4.0;
  private double xVelocity;
  private double yVelocity;

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

  @Override
  public void tick(long now) {
    applyXVelocity();
    applyYVelocity();
    setXVelocity(0.0);
    setYVelocity(0.0);
  }
}
