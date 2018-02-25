package mocha.net;

import com.google.common.eventbus.EventBus;

import mocha.net.packet.Packet;

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
        Packet packet = mochaConnection.readPacket();
        eventBus.post(packet);
        nap();
      } catch(DisconnectedException disconnectedException) {
      }
    }
    System.out.println("Disconnected.");
  }

  private void nap() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
