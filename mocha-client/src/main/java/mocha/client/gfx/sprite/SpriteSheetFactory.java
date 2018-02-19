package mocha.client.gfx.sprite;

import org.springframework.stereotype.Component;

import javafx.scene.image.Image;

@Component
public class SpriteSheetFactory {

  public SpriteSheet newSpriteSheet() {
    return new CachingSpriteSheet(new Image("mocha/gfx/sprites.png"), 16);
  }

  public SpriteSheet newDirtTiles() {
    return new CachingSpriteSheet(new Image("mocha/gfx/tiles/dirt.png"), 16);
  }

  public SpriteSheet newGrassTiles() {
    return new CachingSpriteSheet(new Image("mocha/gfx/tiles/grass.png"), 16);
  }

  public SpriteSheet newWaterTiles() {
    return new CachingSpriteSheet(new Image("mocha/gfx/tiles/water.png"), 16);
  }

  public SpriteSheet newStoneTiles() {
    return new CachingSpriteSheet(new Image("mocha/gfx/tiles/stone.png"), 16);
  }


}
