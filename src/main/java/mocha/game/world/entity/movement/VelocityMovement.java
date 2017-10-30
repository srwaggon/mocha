package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;

@Data
@EqualsAndHashCode(callSuper = true)
public class VelocityMovement extends SimpleMovement {

  private double speed = 2.0;
  private double xVelocity;
  private double yVelocity;
  private EventBus eventBus;
  private Location nextLocation;

  @Builder
  protected VelocityMovement(Location location, Collision collision, double speed, double xVelocity, double yVelocity, EventBus eventBus) {
    super(location, collision);
    this.eventBus = eventBus;
    this.speed = speed;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    nextLocation = location;
  }

  protected void applyXVelocity() {
    nextLocation = nextLocation.add(getXVelocity(), 0);
  }

  protected void applyYVelocity() {
    nextLocation = nextLocation.add(0, getYVelocity());
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
    applyXVelocityIfNotColliding();
    applyYVelocityIfNotColliding();
    eventBus.post(new MovementEvent(this, nextLocation));
    setXVelocity(0.0);
    setYVelocity(0.0);
  }

  protected void applyXVelocityIfNotColliding() {
    if (getCollision().collides(getLocation().add(getXVelocity(), 0))) {
      setXVelocity(0.0);
    }
    applyXVelocity();
  }

  protected void applyYVelocityIfNotColliding() {
    if (getCollision().collides(getLocation().add(0, getYVelocity()))) {
      setYVelocity(0.0);
    }
    applyYVelocity();
  }

  public static class VelocityMovementBuilder extends SimpleMovementBuilder {
    public VelocityMovementBuilder() {
      super();
    }
  }
}
