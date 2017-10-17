package mocha.game.world.entity.movement;

import lombok.Data;

@Data
public class MomentumMovement extends SimpleMovement {

  private double speed = 4.0;
  private double dx;
  private double dy;

  public void applyDx() {
    location.addX(getDx());
  }

  public void applyDy() {
    location.addY(getDy());
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

  @Override
  public void tick(long now) {
    applyDx();
    applyDy();
    setDx(0.0);
    setDy(0.0);
  }
}
