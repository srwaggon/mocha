package mocha.client.gfx.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

  private final String imagePath;
  private final Image image;

  Sprite(String imagePath, Image image) {
    this.imagePath = imagePath;
    this.image = image;
  }

  public void render(GraphicsContext graphicsContext, double spriteX, double spriteY) {
    this.render(graphicsContext, spriteX, spriteY, 1.0);
  }

  private void render(GraphicsContext graphics, double spriteX, double spriteY, double scale) {
    graphics.drawImage(image, spriteX, spriteY, image.getWidth() * scale, image.getHeight() * scale);
  }

  public String getImagePath() {
    return imagePath;
  }

  public Image getImage() {
    return image;
  }

}
