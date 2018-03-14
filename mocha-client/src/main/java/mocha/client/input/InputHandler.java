package mocha.client.input;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.client.MochaClientEventBus;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class InputHandler {

  @Inject
  private MochaClientEventBus eventBus;

  private final Map<KeyCode, InputKey> keyMap = Maps.newHashMap();

  @PostConstruct
  public void init() {
    keyMap.put(KeyCode.UP, InputKey.UP);
    keyMap.put(KeyCode.RIGHT, InputKey.RIGHT);
    keyMap.put(KeyCode.DOWN, InputKey.DOWN);
    keyMap.put(KeyCode.LEFT, InputKey.LEFT);
    keyMap.put(KeyCode.ENTER, InputKey.PICKUP);
  }

  private void down(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode)).ifPresent(this::down);
  }

  private void down(InputKey inputKey) {
    if (!inputKey.isDown()) {
      inputKey.down();
      eventBus.keyDown(inputKey);
    }
  }

  private void up(InputKey inputKey) {
    if (inputKey.isDown()) {
      inputKey.up();
    }
  }

  private void up(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode)).ifPresent(this::up);
  }

  public EventHandler<KeyEvent> getKeyPressedHandler() {
    return event -> this.down(event.getCode());
  }

  public EventHandler<KeyEvent> getKeyReleasedHandler() {
    return event -> this.up(event.getCode());
  }
}
