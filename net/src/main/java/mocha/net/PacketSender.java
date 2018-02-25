package mocha.net;

import com.google.common.eventbus.Subscribe;

public class PacketSender {

  private MochaConnection mochaConnection;

  public PacketSender(MochaConnection mochaConnection) {
    this.mochaConnection = mochaConnection;
  }

  @Subscribe
  public void handle(SendPacket sendPacket) {
    this.mochaConnection.sendPacket(sendPacket.getPacket());
  }
}
