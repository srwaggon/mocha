package mocha.net;

import com.google.common.eventbus.EventBus;

public class PacketListenerFactory {

  private final EventBus eventBus;

  public PacketListenerFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public PacketListener newPacketListener(MochaConnection mochaConnection) {
    return new PacketListener(mochaConnection, eventBus);
  }
}
