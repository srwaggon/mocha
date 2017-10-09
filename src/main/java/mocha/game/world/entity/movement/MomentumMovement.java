package mocha.game.world.entity.movement;

import lombok.Data;

@Data
public class MomentumMovement extends SimpleMovement {

  private double speed = 1.0;
  private double dx;
  private double dy;

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
