package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mocha.game.event.world.entity.movement.MovementEvent;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;

@Data
@EqualsAndHashCode(callSuper = true)
public class VelocityMovement extends SimpleMovement {

  private double speed = 2.0;
  private double xVelocity;
  private double yVelocity;
  protected EventBus eventBus;

  @Builder
  VelocityMovement(Location location, Collision collision, double speed, double xVelocity, double yVelocity, EventBus eventBus) {
    super(location, collision);
    this.eventBus = eventBus;
    this.speed = speed;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
  }

  private Location applyXVelocity(Location location) {
    return location.add(getXVelocity(), 0);
  }

  private Location applyYVelocity(Location location) {
    return location.add(0, getYVelocity());
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
    Location nextLocation = applyYVelocityIfNotColliding(applyXVelocityIfNotColliding(this.getLocation()));
    postNextLocation(nextLocation);
    setXVelocity(0.0);
    setYVelocity(0.0);
  }

  void postNextLocation(Location nextLocation) {
    eventBus.post(new MovementEvent(this, nextLocation));
  }

  Location applyXVelocityIfNotColliding(Location location) {
    if (getCollision().collides(getLocation().add(getXVelocity(), 0))) {
      setXVelocity(0.0);
    }
    return applyXVelocity(location);
  }

  Location applyYVelocityIfNotColliding(Location location) {
    if (getCollision().collides(getLocation().add(0, getYVelocity()))) {
      setYVelocity(0.0);
    }
    return applyYVelocity(location);
  }

  static class VelocityMovementBuilder extends SimpleMovementBuilder {
    VelocityMovementBuilder() {
      super();
    }
  }
}
