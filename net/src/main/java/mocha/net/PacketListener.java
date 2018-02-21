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
        Packet event = mochaConnection.readPacket();
        eventBus.post(event);
      } catch(DisconnectedException disconnectedException) {
        disconnectedException.printStackTrace();
      }
    }
    System.out.println("Disconnected.");
  }
}
