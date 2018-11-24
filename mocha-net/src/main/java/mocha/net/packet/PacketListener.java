package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.exception.DisconnectedException;
import mocha.shared.task.SleepyRunnable;

public class PacketListener implements SleepyRunnable {

  private final MochaConnection connection;
  private final NetworkedMochaEventBus eventBus;
  private final int senderId;
  private PacketHandler packetHandler;

  public PacketListener(NetworkedMochaEventBus eventBus, MochaConnection connection, int senderId, PacketHandler packetHandler) {
    this.connection = connection;
    this.eventBus = eventBus;
    this.senderId = senderId;
    this.packetHandler = packetHandler;
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
      packetHandler.handle(senderId, connection.readPacket());
      nap();
    }
  }

  public void remove() {
    connection.disconnect();
    eventBus.unregister(this);
  }
}
