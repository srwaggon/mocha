package mocha.net.packet;

import com.google.common.eventbus.Subscribe;

public class PacketSender {

  private PacketConnection packetConnection;

  public PacketSender(PacketConnection packetConnection) {
    this.packetConnection = packetConnection;
  }

  @Subscribe
  public void handle(SendPacketEvent sendPacketEvent) {
    this.packetConnection.sendPacket(sendPacketEvent.getPacket());
  }
}
