package mocha.gfx.sprite;

import org.springframework.stereotype.Component;

import javafx.scene.image.Image;

@Component
public class SpriteSheetFactory {

  public SpriteSheet newSpriteSheet() {
    Image image = new Image("sprites.png");
    int spriteSize = 16;
    return new CachingSpriteSheet(image, spriteSize);
  }
}
