package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.exception.DisconnectedException;
import mocha.shared.task.SleepyRunnable;

public class PacketListener implements SleepyRunnable {

  private final MochaConnection connection;
  private final NetworkedMochaEventBus eventBus;
  private PacketResolver packetHandler;

  public PacketListener(
      NetworkedMochaEventBus eventBus,
      MochaConnection connection,
      PacketResolver packetHandler
  ) {
    this.connection = connection;
    this.eventBus = eventBus;
    this.packetHandler = packetHandler;
  }

  @Override
  public void run() {
    try {
      readPackets();
    } catch (DisconnectedException disconnectedException) {
      eventBus.postDisconnectedEvent(0, connection);
    }
  }

  private void readPackets() throws DisconnectedException {
    while (connection.isConnected()) {
      packetHandler.resolve(connection.readPacket());
      nap();
    }
  }

  public void remove() {
    connection.disconnect();
    eventBus.unregister(this);
  }
}
