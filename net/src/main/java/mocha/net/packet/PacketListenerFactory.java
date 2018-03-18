package mocha.net.packet;

import mocha.net.event.NetworkedMochaEventBus;

public class PacketListenerFactory {

  private final NetworkedMochaEventBus eventBus;

  public PacketListenerFactory(NetworkedMochaEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public PacketListener newPacketListener(MochaConnection packetConnection) {
    return new PacketListener(packetConnection, eventBus);
  }
}
