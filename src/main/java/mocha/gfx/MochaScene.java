package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import mocha.game.InputHandler;

@Component
public class MochaScene extends Scene {

  @Inject
  public MochaScene(Parent root, InputHandler inputHandler) {
    super(root, 600, 400);

    addEventHandler(KeyEvent.KEY_PRESSED, inputHandler.getKeyPressedHandler());
    addEventHandler(KeyEvent.KEY_RELEASED, inputHandler.getKeyReleasedHandler());

  }



}
