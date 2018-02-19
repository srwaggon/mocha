package mocha.client.gfx.sprite;

import com.google.common.collect.Maps;

import java.util.Map;

import javafx.scene.image.Image;

public class CachingSpriteSheet extends SpriteSheet {

  private Map<Integer, Image> cachedSprites = Maps.newConcurrentMap();

  CachingSpriteSheet(Image sourceImage, int spriteSize) {
    super(sourceImage, spriteSize);
  }

  @Override
  public Image getSprite(int spriteIndex, double scale) {
    if (cachedSprites.containsKey(spriteIndex)) {
      return cachedSprites.get(spriteIndex);
    }

    Image sprite = super.getSprite(spriteIndex, scale);
    cachedSprites.put(spriteIndex, sprite);
    return sprite;
  }

}