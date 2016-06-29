package mocha.game;

import com.google.common.collect.Lists;

import java.util.Collection;

public class InputKey {

  public static final Collection<InputKey> ALL_KEYS = Lists.newArrayList();
  private boolean isDown;
  private boolean pressed;
  private int clicks;
  private boolean isClicked;


  public InputKey() {
    ALL_KEYS.add(this);
  }

  public final static InputKey LEFT = new InputKey();
  public final static InputKey RIGHT = new InputKey();
  public final static InputKey UP = new InputKey();
  public final static InputKey DOWN = new InputKey();

  public boolean isDown() {
    return isDown;
  }

  public void down() {
    clicks++;
    pressed = true;
  }

  public void up() {
    pressed = false;
  }

  public void tick() {
    isDown = pressed || clicks > 0;
    isClicked = clicks > 0;
    clicks -= clicks > 0 ? 1 : 0;
  }

  public boolean isClicked() {
    return isClicked;
  }

  public static void tickAll() {
    ALL_KEYS.stream().forEach(InputKey::tick);
  }
}
