package mocha.game;

import java.util.Arrays;

public enum InputKey {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  private boolean isDown, isClicked;

  public void up() {
    isDown = false;
  }

  public void down() {
    isDown = true;
    if (!isClicked) {
      isClicked = true;
    }
  }

  public boolean isDown() {
    return isDown;
  }

  public boolean isClicked() {
    return isClicked;
  }

  public void tick() {
    if (isClicked) {
      isClicked = false;
    }
  }

  public static void tickAll() {
    Arrays.stream(values()).forEach(InputKey::tick);
  }
}
