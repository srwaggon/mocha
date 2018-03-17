package mocha.net.packet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendPacketEvent {
  private final Packet packet;
}
