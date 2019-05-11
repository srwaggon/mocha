package mocha.net.packet.event;

import mocha.net.packet.Packet;

public class SendPacketToEvent extends SendPacketEvent {

  private int playerId;

  public SendPacketToEvent(Packet packet, int playerId) {
    super(packet);
    this.playerId = playerId;
  }

  public int getPlayerId() {
    return playerId;
  }
}
