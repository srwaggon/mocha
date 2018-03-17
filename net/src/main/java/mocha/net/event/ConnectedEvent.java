package mocha.net.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.net.packet.PacketConnection;

@Data
@AllArgsConstructor
public class ConnectedEvent {
  private final PacketConnection packetConnection;
}
