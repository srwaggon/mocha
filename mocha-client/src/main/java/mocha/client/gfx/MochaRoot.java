package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.layout.Pane;
import mocha.client.gfx.view.GameView;

@Component
public class MochaRoot extends Pane {

  @Inject
  public MochaRoot(GameView gameView) {
    this.getChildren().add(gameView);
    gameView.widthProperty().bind(this.widthProperty());
    gameView.heightProperty().bind(this.heightProperty());
  }
}
