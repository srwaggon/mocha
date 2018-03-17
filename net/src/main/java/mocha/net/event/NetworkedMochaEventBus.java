package mocha.net.event;

import mocha.game.event.MochaEventBus;
import mocha.net.PacketConnection;
import mocha.net.SendPacketEvent;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketFactory;

public class NetworkedMochaEventBus extends MochaEventBus {

  private PacketFactory packetFactory;

  public NetworkedMochaEventBus(PacketFactory packetFactory) {
    this.packetFactory = packetFactory;
  }

  public void connected(PacketConnection packetConnection) {
    this.post(new ConnectedEvent(packetConnection));
  }

  public void disconnected(PacketConnection packetConnection) {
    this.post(new DisconnectedEvent(packetConnection));
  }

  public void sendPacket(Packet packet) {
    this.post(new SendPacketEvent(packet));
  }
}
