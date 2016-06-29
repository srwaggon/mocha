package mocha.game.world.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mob extends Entity {

  private double width = 32;
  private double height = 32;
  private double speed = 1.0;

  public Mob() {
    super();
  }

  public Mob(int id) {
    super(id);
  }

  @Override
  public void render(GraphicsContext graphics) {
    graphics.setFill(Color.RED);
    graphics.fillRect(getLocation().getX(), getLocation().getY(), width, height);
  }

  public double getSpeed() {
    return speed;
  }
}
