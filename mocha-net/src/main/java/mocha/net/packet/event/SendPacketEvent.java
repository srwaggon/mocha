package mocha.net.packet.event;

import mocha.net.packet.Packet;

public class SendPacketEvent {

  private Packet packet;

  public SendPacketEvent(Packet packet) {
    this.packet = packet;
  }

  public Packet getPacket() {
    return packet;
  }
}
