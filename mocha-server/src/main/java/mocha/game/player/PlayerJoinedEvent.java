package mocha.game.player;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mocha.game.event.MochaEvent;
import mocha.net.packet.MochaConnection;

@AllArgsConstructor
@Value
@ToString
public class PlayerJoinedEvent implements MochaEvent {

  private MochaConnection mochaConnection;
  private Player player;
}
