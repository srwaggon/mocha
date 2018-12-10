package mocha.game.player.event;

import mocha.game.player.Player;

public class PlayerAddedEvent {
  private final Player player;

  public PlayerAddedEvent(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
