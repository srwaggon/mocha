package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.exception.DisconnectedException;

public class PacketListener implements Runnable {

  private MochaConnection connection;
  private final NetworkedMochaEventBus eventBus;

  PacketListener(MochaConnection connection, NetworkedMochaEventBus eventBus) {
    this.connection = connection;
    this.eventBus = eventBus;
  }

  @Override
  public void run() {
    try {
      readPackets();
    } catch (DisconnectedException disconnectedException) {
      eventBus.postDisconnectedEvent(connection);
    }
  }

  private void readPackets() throws DisconnectedException {
    while (connection.isConnected()) {
      eventBus.post(connection.readPacket());
      nap();
    }
  }

  private void nap() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
