package mocha.client.gfx.sprite;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpriteService {

  @Inject
  private SpriteSheetService spriteSheetService;

  public Sprite findById(String spriteId) {
    String[] split = spriteId.split(":");
    String spriteSheetPath = split[0];
    int spriteIndex = Integer.parseInt(split[1]);
    return spriteSheetService.findById(spriteSheetPath)
        .getSprite(spriteIndex, 2.0);
  }

  public Sprite getNext(Sprite sprite) {
    return getNext(sprite.getSpriteIndex(), spriteSheetService.findById(sprite.getImagePath()));
  }

  private Sprite getNext(int spriteIndex, SpriteSheet spriteSheet) {
    int nextSpriteIndex = spriteIndex + 1;
    return nextSpriteIndex < spriteSheet.getSpriteCount()
        ? spriteSheet.getSprite(nextSpriteIndex, 2.0)
        : getNext(0, spriteSheetService.findNext(spriteSheet.getImagePath()));
  }
}
