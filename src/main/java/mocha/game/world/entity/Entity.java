package mocha.game.world.entity;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;

public class Entity implements Renderable {
  private int id;
  private double x;
  private double y;

  Entity(int id) {
    this.id = id;
  }

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

  public int getId() {
    return id;
  }

  @Override
  public void render(GraphicsContext graphics) {
  }

  public void tick() {
    this.x += 1;
  }
}
