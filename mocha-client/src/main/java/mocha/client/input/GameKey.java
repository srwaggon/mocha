package mocha.client.input;

public enum GameKey {

  UNBOUND,

  MENU,

  LEFT,
  RIGHT,
  UP,
  DOWN,

  BACK,
  ACTION1,
  ACTION2,
  ACTION3,

  USE_LEFT_HAND,
  USE_RIGHT_HAND,
  CYCLE_LEFT_HAND,
  CYCLE_RIGHT_HAND,
  ;

  private boolean isDown;
  private boolean pressed;
  private int clicks;
  private boolean isClicked;

  public boolean isDown() {
    return isDown;
  }

  public void down() {
    clicks++;
    pressed = true;
    isDown = true;
  }

  public void up() {
    pressed = false;
    isDown = false;
  }

  public void tick() {
    isDown = pressed || clicks > 0;
    isClicked = clicks > 0;
    clicks -= clicks > 0 ? 1 : 0;
  }

  public boolean isClicked() {
    return isClicked;
  }

}
