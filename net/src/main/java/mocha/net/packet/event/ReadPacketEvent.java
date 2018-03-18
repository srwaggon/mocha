package mocha.net.packet.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.net.packet.Packet;

@Data
@AllArgsConstructor
public class ReadPacketEvent {
  private int senderId;
  private Packet packet;
}
