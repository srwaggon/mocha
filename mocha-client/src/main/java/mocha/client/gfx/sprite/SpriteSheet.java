package mocha.client.gfx.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class SpriteSheet {

  private Image sourceImage;
  private int spriteSize;

  SpriteSheet(Image sourceImage, int spriteSize) {
    this.sourceImage = sourceImage;
    this.spriteSize = spriteSize;
  }

  public Image getSprite(int spriteIndex, double scale) {
    int spriteWidth = (int) (this.spriteSize * scale);
    int spriteHeight = (int) (spriteSize * scale);
    WritableImage spriteImage = new WritableImage(spriteWidth, spriteHeight);
    drawSprite(spriteIndex, 0, 0, scale, spriteImage);
    return spriteImage;
  }

  private void drawSprite(int spriteIndex, int canvasX, int canvasY, double scale, WritableImage destinationImage) {
    int spritesPerRow = (int) (sourceImage.getWidth() / spriteSize);
    int spriteX = spriteSize * (spriteIndex % spritesPerRow);
    int spriteRow = spriteIndex / spritesPerRow;
    int spriteY = spriteRow * spriteSize;

    for (int x = 0; x < spriteSize * scale; x++) {
      for (int y = 0; y < spriteSize * scale; y++) {
        if (isOutsideRectangle(canvasX + x, canvasY + y, destinationImage.getWidth(), destinationImage.getHeight())) {
          continue;
        }

        int spriteSheetX = spriteX + (int) (x / scale);
        int spriteSheetY = spriteY + (int) (y / scale);
        if (isOutsideRectangle(spriteSheetX, spriteSheetY, sourceImage.getWidth(), sourceImage.getHeight())) {
          continue;
        }

        int argb = sourceImage.getPixelReader().getArgb(spriteSheetX, spriteSheetY);
        if (!isPixelTransparent(argb)) {
          destinationImage.getPixelWriter().setArgb(canvasX + x, canvasY + y, argb);
        }
      }
    }
  }

  private boolean isPixelTransparent(int argb) {
    return argb >> 24 == 0;
  }

  private boolean isOutsideRectangle(double x, double y, double width, double height) {
    return isOutOfRange(x, 0, width) || isOutOfRange(y, 0, height);
  }

  private boolean isOutOfRange(double value, double lower, double upper) {
    return value < lower || upper <= value;
  }
}
