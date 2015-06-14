package mocha.game.world.entity;

public class Entity {
  private int id;
  private int x;
  private int y;

  public Entity(int id) {
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getId() {
    return id;
  }
}
