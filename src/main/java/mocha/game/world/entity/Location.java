package mocha.game.world.entity;

public class Location {

  private double x;
  private double y;
  private double dx, dy;

  double getX() {
    return x;
  }

  void setX(double x) {
    this.x = x;
  }

  double getY() {
    return y;
  }

  void setY(double y) {
    this.y = y;
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
    setX(getX() + getDx());
    this.dx = 0.0;
  }

  public void applyDy() {
    setY(getY() + getDy());
    this.dy = 0.0;
  }
}
