package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccelerationMovement extends VelocityMovement {

  private double accelerationRate;
  private double maxXVelocity;
  private double maxYVelocity;
  private double yAcceleration;
  private double xAcceleration;

  @Builder
  protected AccelerationMovement(Location location, Collision collision, double speed, double xVelocity, double yVelocity, double accelerationRate, double maxXVelocity, double maxYVelocity, double yAcceleration, double xAcceleration, EventBus eventBus) {
    super(location, collision, speed, xVelocity, yVelocity, eventBus);
    this.accelerationRate = accelerationRate;
    this.maxXVelocity = maxXVelocity;
    this.maxYVelocity = maxYVelocity;
    this.yAcceleration = yAcceleration;
    this.xAcceleration = xAcceleration;
  }

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
    Location afterXAcceleration = this.applyXAcceleration(this.getLocation());
    Location afterYAcceleration = this.applyYAcceleration(afterXAcceleration);
    postNextLocation(afterYAcceleration);
    this.setXAcceleration(0.0);
    this.setYAcceleration(0.0);
  }

  private Location applyXAcceleration(Location location) {
    double futureXVelocity = this.getXVelocity() + xAcceleration;
    this.addXVelocity(this.getMaxXVelocity() != 0.0 && this.getMaxXVelocity() < Math.abs(futureXVelocity) ? 0.0 : xAcceleration);
    return this.applyXVelocityIfNotColliding(location);
  }

  private Location applyYAcceleration(Location location) {
    double futureYVelocity = this.getYVelocity() + yAcceleration;
    this.addYVelocity(this.getMaxYVelocity() != 0.0 && this.getMaxYVelocity() < Math.abs(futureYVelocity) ? 0.0 : yAcceleration);
    return this.applyYVelocityIfNotColliding(location);
  }

  public static class AccelerationMovementBuilder extends VelocityMovementBuilder {
    public AccelerationMovementBuilder() {
      super();
    }
  }
}
