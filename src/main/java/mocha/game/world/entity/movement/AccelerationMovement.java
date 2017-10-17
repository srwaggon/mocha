package mocha.game.world.entity.movement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
public class AccelerationMovement extends VelocityMovement {

  @Setter
  private double accelerationRate;
  @Setter
  private double maxXVelocity;
  @Setter
  private double maxYVelocity;

  private double yAcceleration;
  private double xAcceleration;

  private void addXVelocity(double xVelocity) {
    this.setXVelocity(this.getXVelocity() + xVelocity);
  }

  private void addYVelocity(double yVelocity) {
    this.setYVelocity(this.getYVelocity() + yVelocity);
  }

  @Override
  public void up() {
    this.setYAcceleration(-accelerationRate);
  }

  @Override
  public void down() {
    this.setYAcceleration(accelerationRate);
  }

  @Override
  public void left() {
    this.setXAcceleration(-accelerationRate);
  }

  @Override
  public void right() {
    this.setXAcceleration(accelerationRate);
  }

  @Override
  public void tick(long now) {
    applyXAcceleration();
    applyYAcceleration();
    this.setXAcceleration(0.0);
    this.setYAcceleration(0.0);
  }

  private void applyXAcceleration() {
    double futureXVelocity = this.getXVelocity() + xAcceleration;
    this.addXVelocity(this.getMaxXVelocity() != 0.0 && this.getMaxXVelocity() < Math.abs(futureXVelocity) ? 0.0 : xAcceleration);
    this.getLocation().addX(this.getXVelocity());
  }

  private void applyYAcceleration() {
    double futureYVelocity = this.getYVelocity() + yAcceleration;
    this.addYVelocity(this.getMaxYVelocity() != 0.0 && this.getMaxYVelocity() < Math.abs(futureYVelocity) ? 0.0 : yAcceleration);
    this.getLocation().addY(this.getYVelocity());
  }
}
