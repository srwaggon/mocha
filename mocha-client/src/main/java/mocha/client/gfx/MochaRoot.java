package mocha.client.gfx;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.scene.Group;

@Component
public class MochaRoot extends Group {

  @Inject
  private BackgroundLayer backgroundLayer;

  @Inject
  private SpriteLayer spriteLayer;

  @Inject
  private EventBus eventBus;

  @PostConstruct
  public void init() {
    this.getChildren().add(backgroundLayer);
    this.getChildren().add(spriteLayer);
    eventBus.register(spriteLayer);
  }
}
