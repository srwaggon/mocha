package mocha.game.world.entity.movement;

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

  @Builder
  protected VelocityMovement(Location location, Collision collision, double speed, double xVelocity, double yVelocity) {
    super(location, collision);
    this.speed = speed;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
  }

  protected void applyXVelocity() {
    location.addX(getXVelocity());
  }

  protected void applyYVelocity() {
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
    applyXVelocityIfNotColliding();
    applyYVelocityIfNotColliding();
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
