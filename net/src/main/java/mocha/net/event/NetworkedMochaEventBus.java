package mocha.net.event;

import mocha.game.event.MochaEventbus;
import mocha.net.MochaConnection;
import mocha.net.SendPacketEvent;
import mocha.net.packet.Packet;

public class NetworkedMochaEventBus extends MochaEventbus {

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
