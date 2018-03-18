package mocha.net.packet.event;

import lombok.Data;
import mocha.net.packet.Packet;

@Data
public class SendPacketToEvent extends SendPacketEvent {
  private final int playerId;

  public SendPacketToEvent(Packet packet, int playerId) {
    super(packet);
    this.playerId = playerId;
  }
}
