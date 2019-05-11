package mocha.net.event;

import mocha.game.event.MochaEvent;
import mocha.net.packet.MochaConnection;

public class ConnectedEvent implements MochaEvent {

  private final MochaConnection mochaConnection;

  public ConnectedEvent(MochaConnection mochaConnection) {
    this.mochaConnection = mochaConnection;
  }

  public MochaConnection getMochaConnection() {
    return mochaConnection;
  }
}
