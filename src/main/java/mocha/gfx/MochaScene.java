package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import mocha.game.InputHandler;
import mocha.game.world.tile.Tile;

@Component
public class MochaScene extends Scene {

  @Inject
  public MochaScene(Parent root, InputHandler inputHandler) {
    super(root, Tile.SIZE * 18, Tile.SIZE * 12);

    addEventHandler(KeyEvent.KEY_PRESSED, inputHandler.getKeyPressedHandler());
    addEventHandler(KeyEvent.KEY_RELEASED, inputHandler.getKeyReleasedHandler());
  }

}
