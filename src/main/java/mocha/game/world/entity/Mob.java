package mocha.game.world.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mob extends Entity {

  private int width = 32;
  private int height = 32;

  public Mob(int id) {
    super(id);
  }

  @Override
  public void render(GraphicsContext graphics) {
    graphics.setFill(Color.RED);
    graphics.fillRect(getX(), getY(), width, height);
  }
}
