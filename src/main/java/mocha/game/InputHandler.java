package mocha.game;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class InputHandler {
  private final Map<KeyCode, InputKey> keyMap = Maps.newHashMap();

  InputHandler() {
    keyMap.put(KeyCode.UP, InputKey.UP);
    keyMap.put(KeyCode.RIGHT, InputKey.RIGHT);
    keyMap.put(KeyCode.DOWN, InputKey.DOWN);
    keyMap.put(KeyCode.LEFT, InputKey.LEFT);
  }

  void put(KeyCode keyCode, InputKey inputKey) {
    keyMap.put(keyCode, inputKey);
  }

  void down(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode))
        .orElse(InputKey.UNBOUND)
        .down();
  }

  void up(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode))
        .orElse(InputKey.UNBOUND)
        .up();
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
