package mocha.game.player.event;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.game.player.Player;

@Value
@AllArgsConstructor
public class PlayerRemovedEvent implements MochaEvent {

  private Player player;

}
