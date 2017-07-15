package mocha.gfx;

import org.springframework.stereotype.Component;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

@Component
public class SpriteSheet {

  private final Image sourceImage = new Image("sprites.png");
  public final int spriteSize = 16;

  private final int PURE_BLACK = -16777216;

  public void drawSprite(int spriteIndex, int canvasX, int canvasY, double scale, WritableImage destinationImage) {
    int spritesPerRow = (int) (sourceImage.getWidth() / spriteSize);
    int spriteX = spriteSize * (spriteIndex % spritesPerRow);
    int spriteRow = spriteIndex / spritesPerRow;
    int spriteY = spriteRow * spriteSize;

    for (int x = 0; x < spriteSize * scale; x++) {
      for (int y = 0; y < spriteSize * scale; y++) {
        if (!isWithinRectangle(canvasX + x, canvasY + y, destinationImage.getWidth(), destinationImage.getHeight())) {
          continue;
        }

        double spriteSheetX = spriteX + (x / scale);
        double spriteSheetY = spriteY + (y / scale);
        if (!isWithinRectangle(spriteSheetX, spriteSheetY, sourceImage.getWidth(), sourceImage.getHeight())) {
          continue;
        }

        int argb = sourceImage.getPixelReader().getArgb((int) spriteSheetX, (int) spriteSheetY);
        if (argb == PURE_BLACK) {
          continue;
        }
        destinationImage.getPixelWriter().setArgb(canvasX + x, canvasY + y, argb);
      }
    }
  }

  private boolean isWithinRectangle(double x, double y, double width, double height) {
    return isInRange(x, 0, width) && isInRange(y, 0, height);
  }

  private boolean isInRange(double x, double lower, double upper) {
    return lower <= x && x < upper;
  }
}
