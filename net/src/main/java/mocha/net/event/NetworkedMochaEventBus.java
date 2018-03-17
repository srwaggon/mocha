package mocha.net.event;

import mocha.game.event.MochaEventBus;
import mocha.net.MochaConnection;
import mocha.net.SendPacketEvent;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketFactory;

public class NetworkedMochaEventBus extends MochaEventBus {

  private PacketFactory packetFactory;

  public NetworkedMochaEventBus(PacketFactory packetFactory) {
    this.packetFactory = packetFactory;
  }

  public void connected(MochaConnection mochaConnection) {
    this.post(new ConnectedEvent(mochaConnection));
  }

  public void disconnected(MochaConnection mochaConnection) {
    this.post(new DisconnectedEvent(mochaConnection));
  }

  public void sendPacket(Packet packet) {
    this.post(new SendPacketEvent(packet));
  }
}
