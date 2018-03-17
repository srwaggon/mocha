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
import mocha.client.event.MochaClientEventBus;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class InputHandler {

  @Inject
  private MochaClientEventBus eventBus;

  private final Map<KeyCode, GameKey> keyMap = Maps.newHashMap();

  @PostConstruct
  public void init() {
    keyMap.put(KeyCode.UP, GameKey.UP);
    keyMap.put(KeyCode.RIGHT, GameKey.RIGHT);
    keyMap.put(KeyCode.DOWN, GameKey.DOWN);
    keyMap.put(KeyCode.LEFT, GameKey.LEFT);
    keyMap.put(KeyCode.ENTER, GameKey.PICKUP);
  }

  private void down(KeyCode keyCode) {
    Optional.ofNullable(keyMap.get(keyCode)).ifPresent(this::down);
  }

  private void down(GameKey gameKey) {
    if (!gameKey.isDown()) {
      gameKey.down();
      eventBus.keyDown(gameKey);
    }
  }

  private void up(GameKey gameKey) {
    if (gameKey.isDown()) {
      gameKey.up();
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
