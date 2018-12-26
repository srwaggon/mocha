package mocha.net.packet;

import com.google.common.eventbus.Subscribe;

import mocha.net.packet.event.SendPacketEvent;

public class PacketSender {

  private PacketConnection packetConnection;

  PacketSender(PacketConnection packetConnection) {
    this.packetConnection = packetConnection;
  }

  @Subscribe
  public void handle(SendPacketEvent sendPacketEvent) {
    this.packetConnection.send(sendPacketEvent.getPacket());
  }
}
