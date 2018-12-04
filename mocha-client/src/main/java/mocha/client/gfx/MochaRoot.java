package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Group;
import mocha.client.gfx.view.GameView;

@Component
public class MochaRoot extends Group {

  @Inject
  public MochaRoot(GameView gameView) {
    this.getChildren().add(gameView);
  }
}
