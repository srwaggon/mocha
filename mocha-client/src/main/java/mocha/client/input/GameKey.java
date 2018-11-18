package mocha.client.input;

public enum GameKey {

  UNBOUND,
  LEFT,
  RIGHT,
  UP,
  DOWN,
  PICKUP
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
