package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import mocha.game.world.Location;

@Component
public class MochaCanvas extends Canvas {

  private int width = 600;
  private int height = 400;
  private final double DEFAULT_SCALE = 1.0;


  @Inject
  private SpriteSheet spriteSheet;

  private final WritableImage writableImage = new WritableImage(width, height);

  public MochaCanvas() {
    super(600, 400);
  }

  public void drawSprite(int spriteIndex, Location location) {
    drawSprite(spriteIndex, (int) location.getX(), (int) location.getY());
  }

  public void drawSprite(int spriteIndex, Location location, double scale) {
    drawSprite(spriteIndex, (int) location.getX(), (int) location.getY(), scale);
  }

  public void drawSprite(int spriteIndex, int x, int y) {
    drawSprite(spriteIndex, x, y, DEFAULT_SCALE);
  }

  public void drawSprite(int spriteIndex, int x, int y, double scale) {
    spriteSheet.drawSprite(spriteIndex, x, y, scale, writableImage);
  }

  public void render() {
    getGraphicsContext2D().drawImage(writableImage,
        0, 0, writableImage.getWidth(), writableImage.getHeight(),
        0, 0, width, height);
  }
}
