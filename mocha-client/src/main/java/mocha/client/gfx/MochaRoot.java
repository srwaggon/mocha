package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.scene.Group;

@Component
public class MochaRoot extends Group {

  private BackgroundLayer backgroundLayer;
  private SpriteLayer spriteLayer;

  @Inject
  public MochaRoot(BackgroundLayer backgroundLayer, SpriteLayer spriteLayer) {
    this.backgroundLayer = backgroundLayer;
    this.spriteLayer = spriteLayer;
  }

  @PostConstruct
  public void init() {
    this.getChildren().add(backgroundLayer);
    this.getChildren().add(spriteLayer);
  }
}
