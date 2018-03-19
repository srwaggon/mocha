package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.exception.DisconnectedException;
import mocha.shared.task.SleepyRunnable;

public class PacketListener implements SleepyRunnable {

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
}
