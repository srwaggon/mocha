package mocha.net.event;

import mocha.net.packet.MochaConnection;

public class ConnectedEvent {
  private final MochaConnection mochaConnection;

  public ConnectedEvent(MochaConnection mochaConnection) {
    this.mochaConnection = mochaConnection;
  }

  public MochaConnection getMochaConnection() {
    return mochaConnection;
  }
}
