package mocha.net.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import mocha.net.packet.MochaConnection;

@Data
@AllArgsConstructor
@ToString
public class DisconnectedEvent {
  private int senderId;
  private MochaConnection mochaConnection;
}
