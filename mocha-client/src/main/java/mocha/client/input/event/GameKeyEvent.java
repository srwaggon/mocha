package mocha.client.input.event;

import mocha.client.input.GameKey;

public class GameKeyEvent {

  private GameKey gameKey;
  private boolean isDown;

  public GameKeyEvent(GameKey gameKey, boolean isDown) {
    this.gameKey = gameKey;
    this.isDown = isDown;
  }

  public GameKey getGameKey() {
    return gameKey;
  }

  public boolean isDown() {
    return isDown;
  }
}
