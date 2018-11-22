package mocha.game.event;

import mocha.game.Player;

public class PlayerAddedEvent {
  private final Player player;

  PlayerAddedEvent(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
