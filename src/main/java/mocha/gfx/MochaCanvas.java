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
  private final int scale = 3;


  @Inject
  private SpriteSheet spriteSheet;

  private final WritableImage writableImage = new WritableImage(600 / scale, 400 / scale);

  public MochaCanvas() {
    super(600, 400);
  }

  public void drawSprite(int spriteIndex, Location location) {
    spriteSheet.drawSprite(spriteIndex, (int) location.getX(), (int) location.getY(), writableImage);
  }

  public void drawSprite(int spriteIndex, int x, int y) {
    spriteSheet.drawSprite(spriteIndex, x, y, writableImage);
  }

  public void render() {
    getGraphicsContext2D().drawImage(writableImage,
        0, 0, writableImage.getWidth(), writableImage.getHeight(),
        0, 0, width, height);
  }
}
