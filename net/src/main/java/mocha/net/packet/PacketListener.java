package mocha.net.packet;

import com.google.common.eventbus.EventBus;

import mocha.net.exception.DisconnectedException;

public class PacketListener implements Runnable {

  private PacketConnection packetConnection;
  private final EventBus eventBus;

  PacketListener(PacketConnection packetConnection, EventBus eventBus) {
    this.packetConnection = packetConnection;
    this.eventBus = eventBus;
  }

  @Override
  public void run() {
    while (packetConnection.isConnected()) {
      try {
        Packet packet = packetConnection.readPacket();
        eventBus.post(packet);
        nap();
      } catch(DisconnectedException disconnectedException) {
      }
    }
    System.out.println("Disconnected.");
  }

  private void nap() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
