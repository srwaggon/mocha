package mocha.net.event;

import mocha.game.event.MochaEvent;
import mocha.net.packet.MochaConnection;

public class DisconnectedEvent implements MochaEvent {
  private final int senderId;
  private final MochaConnection mochaConnection;

  public DisconnectedEvent(int senderId, MochaConnection mochaConnection) {
    this.senderId = senderId;
    this.mochaConnection = mochaConnection;
  }

  public int getSenderId() {
    return senderId;
  }

  public MochaConnection getMochaConnection() {
    return mochaConnection;
  }
}
