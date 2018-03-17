package mocha.net.event;

import lombok.AllArgsConstructor;
import mocha.net.packet.PacketConnection;

@AllArgsConstructor
public class DisconnectedEvent {
  private final PacketConnection packetConnection;
}
