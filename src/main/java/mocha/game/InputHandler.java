package mocha.game;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

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
import mocha.game.input.KeyDownEvent;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class InputHandler {

  @Inject
  private EventBus eventBus;

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
    InputKey inputKey = Optional.ofNullable(keyMap.get(keyCode))
        .orElse(InputKey.UNBOUND);
    inputKey.down();

    eventBus.post(new KeyDownEvent(inputKey));
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
