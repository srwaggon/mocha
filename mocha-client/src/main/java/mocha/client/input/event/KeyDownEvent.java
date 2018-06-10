package mocha.client.input.event;

import mocha.client.input.GameKey;

public class KeyDownEvent {
  private final GameKey gameKey;

  public KeyDownEvent(GameKey gameKey) {
    this.gameKey = gameKey;
  }

  public GameKey getGameKey() {
    return gameKey;
  }
}
