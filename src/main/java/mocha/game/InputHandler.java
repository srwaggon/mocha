package mocha.game;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class InputHandler {
  private final Map<KeyCode, InputKey> keyMap = Maps.newHashMap();

  public InputHandler() {
    keyMap.put(KeyCode.UP, InputKey.UP);
    keyMap.put(KeyCode.RIGHT, InputKey.RIGHT);
    keyMap.put(KeyCode.DOWN, InputKey.DOWN);
    keyMap.put(KeyCode.LEFT, InputKey.LEFT);
  }

  public void put(KeyCode keyCode, InputKey inputKey) {
    keyMap.put(keyCode, inputKey);
  }

  public InputKey getKey(KeyCode keyCode) {
    return keyMap.get(keyCode);
  }

  public void down(KeyCode keyCode) {
    keyMap.get(keyCode).down();
  }

  public void up(KeyCode keyCode) {
    keyMap.get(keyCode).up();
  }

  public boolean isDown(InputKey inputKey) {
    return inputKey.isDown();
  }

  public EventHandler<KeyEvent> getKeyPressedHandler() {
    return event -> this.down(event.getCode());
  }

  public EventHandler<KeyEvent> getKeyReleasedHandler() {
    return event -> this.up(event.getCode());
  }
}
