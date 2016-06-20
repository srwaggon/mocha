package mocha.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class InputHandlerProvider {

  enum KEYS {
    UP,
    DOWN,
    LEFT,
    RIGHT,
  }

  public EventHandler<KeyEvent> getKeyUpHandler() {
    return null;
  }

  public EventHandler<KeyEvent> getKeyDownHandler() {
    return null;
  }

}
