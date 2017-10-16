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

  public InputHandler() {
    keyMap.put(KeyCode.UP, InputKey.UP);
    keyMap.put(KeyCode.RIGHT, InputKey.RIGHT);
    keyMap.put(KeyCode.DOWN, InputKey.DOWN);
    keyMap.put(KeyCode.LEFT, InputKey.LEFT);
  }

  private void down(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode))
        .orElse(InputKey.UNBOUND)
        .down();
  }

  private void up(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode))
        .orElse(InputKey.UNBOUND)
        .up();
  }

  public EventHandler<KeyEvent> getKeyPressedHandler() {
    return event -> this.down(event.getCode());
  }

  public EventHandler<KeyEvent> getKeyReleasedHandler() {
    return event -> this.up(event.getCode());
  }
}
