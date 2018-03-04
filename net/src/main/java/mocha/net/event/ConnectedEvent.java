package mocha.net.event;

import lombok.AllArgsConstructor;
import mocha.net.MochaConnection;

@AllArgsConstructor
public class ConnectedEvent {
  private final MochaConnection mochaConnection;
}
