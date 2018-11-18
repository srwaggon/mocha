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
import mocha.client.event.ClientEventBus;

@Component
public class InputHandler {

  private final Map<KeyCode, GameKey> keyMap = Maps.newHashMap();

  private ClientEventBus eventBus;

  @Inject
  public InputHandler(ClientEventBus eventBus) {
    this.eventBus = eventBus;
  }

  @PostConstruct
  public void init() {
    keyMap.put(KeyCode.UP, GameKey.UP);
    keyMap.put(KeyCode.RIGHT, GameKey.RIGHT);
    keyMap.put(KeyCode.DOWN, GameKey.DOWN);
    keyMap.put(KeyCode.LEFT, GameKey.LEFT);
    keyMap.put(KeyCode.ENTER, GameKey.PICKUP);
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

  public EventHandler<KeyEvent> getKeyPressedHandler() {
    return event -> Optional.ofNullable(keyMap.get(event.getCode())).ifPresent(this::down);
  }

  public EventHandler<KeyEvent> getKeyReleasedHandler() {
    return event -> Optional.ofNullable(keyMap.get(event.getCode())).ifPresent(this::up);
  }
}
