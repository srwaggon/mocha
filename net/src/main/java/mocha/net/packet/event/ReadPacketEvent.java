package mocha.net.packet.event;

import mocha.net.packet.Packet;

public class ReadPacketEvent {
  private final int senderId;
  private final Packet packet;

  public ReadPacketEvent(int senderId, Packet packet) {
    this.senderId = senderId;
    this.packet = packet;
  }

  public int getSenderId() {
    return senderId;
  }

  public Packet getPacket() {
    return packet;
  }
}
