package mocha.net.event;

import mocha.game.event.MochaEventbus;
import mocha.net.MochaConnection;

public class NetworkedMochaEventBus extends MochaEventbus {

  public void connected(MochaConnection mochaConnection) {
    this.post(new ConnectedEvent(mochaConnection));
  }

  public void disconnected(MochaConnection mochaConnection) {
    this.post(new DisconnectedEvent(mochaConnection));
  }
}
