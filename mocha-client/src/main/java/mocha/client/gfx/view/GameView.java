package mocha.client.gfx.view;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.gfx.BackgroundLayer;
import mocha.client.gfx.SpriteLayer;

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
