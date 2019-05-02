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
  InputHandler(ClientEventBus eventBus) {
    this.eventBus = eventBus;
  }

  @PostConstruct
  void init() {
    keyMap.put(KeyCode.W, GameKey.UP);
    keyMap.put(KeyCode.A, GameKey.LEFT);
    keyMap.put(KeyCode.S, GameKey.DOWN);
    keyMap.put(KeyCode.D, GameKey.RIGHT);
    keyMap.put(KeyCode.ESCAPE, GameKey.BACK);
    keyMap.put(KeyCode.SPACE, GameKey.ACTION1);
    keyMap.put(KeyCode.F, GameKey.ACTION2);
    keyMap.put(KeyCode.E, GameKey.ACTION3);
    keyMap.put(KeyCode.T, GameKey.USE_LEFT_HAND);
    keyMap.put(KeyCode.R, GameKey.USE_RIGHT_HAND);
    keyMap.put(KeyCode.Z, GameKey.CYCLE_LEFT_HAND);
    keyMap.put(KeyCode.X, GameKey.CYCLE_RIGHT_HAND);
    keyMap.put(KeyCode.ENTER, GameKey.MENU);
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
      eventBus.keyUp(gameKey);
    }
  }

  public EventHandler<KeyEvent> getKeyPressedHandler() {
    return event -> Optional.ofNullable(keyMap.get(event.getCode())).ifPresent(this::down);
  }

  public EventHandler<KeyEvent> getKeyReleasedHandler() {
    return event -> Optional.ofNullable(keyMap.get(event.getCode())).ifPresent(this::up);
  }
}
