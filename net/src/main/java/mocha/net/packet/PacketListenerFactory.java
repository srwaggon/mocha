package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;

public class PacketListenerFactory {

  private final NetworkedMochaEventBus eventBus;

  public PacketListenerFactory(NetworkedMochaEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public PacketListener newPacketListener(MochaConnection packetConnection, int senderId, PacketHandler packetHandler) {
    return new PacketListener(eventBus, packetConnection, senderId, packetHandler);
  }
}
