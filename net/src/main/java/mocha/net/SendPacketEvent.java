package mocha.net;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.net.packet.Packet;

@Data
@AllArgsConstructor
public class SendPacketEvent {
  private final Packet packet;
}
