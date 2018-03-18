package mocha.net.packet.event;

import lombok.Data;
import mocha.game.world.Location;
import mocha.net.packet.Packet;

@Data
public class SendPacketToAllNearEvent extends SendPacketEvent {
  private final Location location;
  private final int distance;

  public SendPacketToAllNearEvent(Packet packet, Location location, int distance) {
    super(packet);
    this.location = location;
    this.distance = distance;
  }
}
