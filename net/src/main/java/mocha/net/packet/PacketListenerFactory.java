package mocha.net.packet;

import com.google.common.eventbus.EventBus;

public class PacketListenerFactory {

  private final EventBus eventBus;

  public PacketListenerFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public PacketListener newPacketListener(PacketConnection packetConnection) {
    return new PacketListener(packetConnection, eventBus);
  }
}
