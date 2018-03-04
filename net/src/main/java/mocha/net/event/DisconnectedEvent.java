package mocha.net.event;

import lombok.AllArgsConstructor;
import mocha.net.MochaConnection;

@AllArgsConstructor
public class DisconnectedEvent {
  private final MochaConnection mochaConnection;
}
