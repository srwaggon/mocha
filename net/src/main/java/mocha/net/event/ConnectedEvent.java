package mocha.net.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.net.packet.MochaConnection;

@Data
@AllArgsConstructor
public class ConnectedEvent {
  private final MochaConnection mochaConnection;
}
