package mocha.net.event;

import mocha.game.event.MochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.SendPacketEvent;

public class NetworkedMochaEventBus extends MochaEventBus {

  public void postConnectedEvent(MochaConnection mochaConnection) {
    this.post(new ConnectedEvent(mochaConnection));
  }

  public void postDisconnectedEvent(MochaConnection mochaConnection) {
    this.post(new DisconnectedEvent(mochaConnection));
  }

  public void postSendPacketEvent(Packet packet) {
    this.post(new SendPacketEvent(packet));
  }

}
