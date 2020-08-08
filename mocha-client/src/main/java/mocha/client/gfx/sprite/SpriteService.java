package mocha.client.gfx.sprite;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpriteService {

  @Inject
  private SpriteSheetService spriteSheetService;
  @Inject
  private ScaleProvider scaleProvider;


  public Sprite findById(String spriteId, int spriteIndex) {
    return spriteSheetService.findById(spriteId)
        .getSprite(spriteIndex, getScale() * 2.0);
  }

  private double getScale() {
    return scaleProvider.getScale();
  }
}
