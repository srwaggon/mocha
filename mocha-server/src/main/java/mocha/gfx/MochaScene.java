package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import mocha.game.InputHandler;
import mocha.game.world.chunk.Chunk;

@Component
public class MochaScene extends Scene {

  @Inject
  public MochaScene(MochaRoot root, InputHandler inputHandler) {
    super(root, Chunk.getWidth(), Chunk.getHeight());

    addEventHandler(KeyEvent.KEY_PRESSED, inputHandler.getKeyPressedHandler());
    addEventHandler(KeyEvent.KEY_RELEASED, inputHandler.getKeyReleasedHandler());
  }

}
