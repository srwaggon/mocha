package mocha.client.gfx.sprite;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpriteService {

  @Inject
  private SpriteSheetService spriteSheetService;

  public Sprite findById(String spriteId) {
    String[] split = spriteId.split("::");
    String spriteSheetPath = split[0];
    int spriteIndex = Integer.parseInt(split[1]);
    return spriteSheetService.findById(spriteSheetPath).getSprite(spriteIndex, 2.0);
  }

  public Sprite getNext(String spriteId) {
    return getNext(findById(spriteId));
  }

  public Sprite getNext(Sprite sprite) {
    String spriteSheetPath = sprite.getImagePath();
    SpriteSheet spriteSheet = spriteSheetService.findById(spriteSheetPath);
    int spriteIndex = sprite.getSpriteIndex();
    return getNext(spriteSheet, spriteIndex + 1);
  }

  private Sprite getNext(SpriteSheet spriteSheet, int spriteIndex) {
    return spriteIndex < spriteSheet.getSpriteCount()
        ? spriteSheet.getSprite(spriteIndex, 2.0)
        : getNext(spriteSheetService.findNext(spriteSheet.getImagePath()), 0);
  }
}
