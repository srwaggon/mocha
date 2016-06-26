package mocha.game;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class InputHandlerProvider {
  private Map<KeyCode, InputKey> keyMap = new HashMap<>();

  public InputHandlerProvider() {
    keyMap.put(KeyCode.UP, InputKey.UP);
    keyMap.put(KeyCode.RIGHT, InputKey.RIGHT);
    keyMap.put(KeyCode.DOWN, InputKey.DOWN);
    keyMap.put(KeyCode.LEFT, InputKey.LEFT);
  }

  public EventHandler<KeyEvent> getKeyUpHandler() {
    return event -> keyMap.get(event.getCode()).up();
  }

  public EventHandler<KeyEvent> getKeyDownHandler() {
    return event -> keyMap.get(event.getCode()).down();
  }

}
