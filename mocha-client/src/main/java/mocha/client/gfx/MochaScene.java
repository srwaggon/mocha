package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import mocha.client.input.InputHandler;

@Component
public class MochaScene extends Scene {

  @Inject
  public MochaScene(
      Group root,
      InputHandler inputHandler,
      WindowDimensions windowDimensions
  ) {
    super(root, windowDimensions.getWidthProperty().get(), windowDimensions.getHeightProperty().get());

    addEventHandler(KeyEvent.KEY_PRESSED, inputHandler.getKeyPressedHandler());
    addEventHandler(KeyEvent.KEY_RELEASED, inputHandler.getKeyReleasedHandler());

    windowDimensions.getWidthProperty().bind(this.widthProperty());
    windowDimensions.getHeightProperty().bind(this.heightProperty());
  }

}
