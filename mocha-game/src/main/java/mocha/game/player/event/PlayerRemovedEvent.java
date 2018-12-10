package mocha.game.player.event;

import mocha.game.player.Player;

public class PlayerRemovedEvent {
  private final Player player;

  public PlayerRemovedEvent(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
