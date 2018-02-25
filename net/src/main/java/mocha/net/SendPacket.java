package mocha.net;

import mocha.net.packet.Packet;

public class SendPacket {

  private final Packet packet;

  public SendPacket(Packet packet) {
    this.packet = packet;
  }

  public Packet getPacket() {
    return packet;
  }
}
