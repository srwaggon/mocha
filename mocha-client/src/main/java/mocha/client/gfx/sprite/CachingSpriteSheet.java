package mocha.client.gfx.sprite;

import com.google.common.collect.Maps;

import java.util.Map;

public class CachingSpriteSheet extends SpriteSheet {

  private Map<Integer, Sprite> cachedSprites = Maps.newConcurrentMap();

  CachingSpriteSheet(String path, int spriteSize) {
    super(path, spriteSize);
  }

  @Override
  public Sprite getSprite(int spriteIndex, double scale) {
    if (cachedSprites.containsKey(spriteIndex)) {
      return cachedSprites.get(spriteIndex);
    }

    Sprite sprite = super.getSprite(spriteIndex, scale);
    cachedSprites.put(spriteIndex, sprite);
    return sprite;
  }

}