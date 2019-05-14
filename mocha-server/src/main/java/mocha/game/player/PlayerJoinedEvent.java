package mocha.game.player;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.net.packet.MochaConnection;

@Value
@AllArgsConstructor
public class PlayerJoinedEvent implements MochaEvent {

  private MochaConnection mochaConnection;
  private Player player;
}
