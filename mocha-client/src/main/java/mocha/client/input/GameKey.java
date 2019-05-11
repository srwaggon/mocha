package mocha.client.input;

import java.util.Optional;

import mocha.client.input.event.GameKeyEvent;
import mocha.game.world.Direction;

import static mocha.game.world.Direction.EAST;
import static mocha.game.world.Direction.NORTH;
import static mocha.game.world.Direction.SOUTH;
import static mocha.game.world.Direction.WEST;

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

  static Optional<Direction> getDirection(GameKeyEvent gameKeyEvent) {
    switch (gameKeyEvent.getGameKey()) {
      case UP:
        return Optional.of(NORTH);
      case RIGHT:
        return Optional.of(EAST);
      case DOWN:
        return Optional.of(SOUTH);
      case LEFT:
        return Optional.of(WEST);
      default:
        return Optional.empty();
    }
  }

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
