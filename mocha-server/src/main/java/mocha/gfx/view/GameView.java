package mocha.gfx.view;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.gfx.BackgroundLayer;
import mocha.gfx.SpriteLayer;

@Component
public class GameView {

  @Inject
  private BackgroundLayer backgroundLayer;

  @Inject
  private SpriteLayer spriteLayer;

  public void render() {
    backgroundLayer.render();
    spriteLayer.render();
  }

}
