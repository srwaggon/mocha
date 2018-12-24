package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import mocha.client.input.InputHandler;

@Component
public class MochaScene extends Scene {

  @Inject
  public MochaScene(MochaRoot root, InputHandler inputHandler) {
    super(root, 1024, 768);

    addEventHandler(KeyEvent.KEY_PRESSED, inputHandler.getKeyPressedHandler());
    addEventHandler(KeyEvent.KEY_RELEASED, inputHandler.getKeyReleasedHandler());
  }

}
