package mocha.game;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;

import javafx.scene.input.KeyCode;

@Component
public class InputHandler {
  private final Map<KeyCode, InputKey> keyMap = Maps.newHashMap();

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
}
