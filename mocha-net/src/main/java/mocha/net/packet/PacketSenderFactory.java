package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;

public class PacketSenderFactory {

  private NetworkedMochaEventBus networkedMochaEventBus;

  public PacketSenderFactory(NetworkedMochaEventBus networkedMochaEventBus) {
    this.networkedMochaEventBus = networkedMochaEventBus;
  }

  public PacketSender newPacketSender(PacketConnection packetConnection) {
    PacketSender packetSender = new PacketSender(packetConnection);
    networkedMochaEventBus.register(packetSender);
    return packetSender;
  }
}
