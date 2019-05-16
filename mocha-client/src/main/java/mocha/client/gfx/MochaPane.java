package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.layout.Pane;
import mocha.client.gfx.view.GameView;

@Component
public class MochaPane extends Pane {

  @Inject
  public MochaPane(
      GameView gameView,
      WindowDimensions windowDimensions
  ) {
    this.getChildren().add(gameView);

    gameView.widthProperty().bind(windowDimensions.getWidthProperty());
    gameView.heightProperty().bind(windowDimensions.getHeightProperty());
  }
}
