package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.exception.DisconnectedException;

public class PacketListener implements Runnable {

  private final MochaConnection connection;
  private final NetworkedMochaEventBus eventBus;
  private final int senderId;

  PacketListener(NetworkedMochaEventBus eventBus, MochaConnection connection, int senderId) {
    this.connection = connection;
    this.eventBus = eventBus;
    this.senderId = senderId;
  }

  @Override
  public void run() {
    try {
      readPackets();
    } catch (DisconnectedException disconnectedException) {
      eventBus.postDisconnectedEvent(senderId, connection);
    }
  }

  private void readPackets() throws DisconnectedException {
    while (connection.isConnected()) {
      eventBus.postReadPacketEvent(senderId, connection.readPacket());
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
