package mocha.net;

import com.google.common.eventbus.EventBus;

public class PacketListener implements Runnable {

  private MochaConnection mochaConnection;
  private final EventBus eventBus;

  PacketListener(MochaConnection mochaConnection, EventBus eventBus) {
    this.mochaConnection = mochaConnection;
    this.eventBus = eventBus;
  }

  @Override
  public void run() {
    while (mochaConnection.isConnected()) {
      try {
        eventBus.post(mochaConnection.readPacket());
      } catch(DisconnectedException disconnectedException) {
      }
    }
    System.out.println("Disconnected.");
  }
}
