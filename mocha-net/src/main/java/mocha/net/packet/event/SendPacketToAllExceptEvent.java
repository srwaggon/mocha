package mocha.net.packet.event;

import java.util.Set;

import mocha.net.packet.Packet;

public class SendPacketToAllExceptEvent extends SendPacketEvent {

  private Set<Integer> playerIds;

  public SendPacketToAllExceptEvent(Packet packet, Set<Integer> playerIds) {
    super(packet);
    this.playerIds = playerIds;
  }

  public Set<Integer> getPlayerIds() {
    return playerIds;
  }
}
