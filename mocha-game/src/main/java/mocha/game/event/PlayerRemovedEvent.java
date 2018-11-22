package mocha.game.event;

import mocha.game.Player;

public class PlayerRemovedEvent {
  private final Player player;

  PlayerRemovedEvent(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
