package mocha.net.packet.event;

import java.util.Set;

import lombok.Data;
import mocha.net.packet.Packet;

@Data
public class SendPacketToAllExceptEvent extends SendPacketEvent {

  private final Set<Integer> playerIds;

  public SendPacketToAllExceptEvent(Packet packet, Set<Integer> playerIds) {
    super(packet);
    this.playerIds = playerIds;
  }
}
