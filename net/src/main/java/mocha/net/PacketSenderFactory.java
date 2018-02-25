package mocha.net;

import com.google.common.eventbus.EventBus;

public class PacketSenderFactory {

  private EventBus eventBus;

  public PacketSenderFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public PacketSender newPacketSender(MochaConnection mochaConnection) {
    PacketSender packetSender = new PacketSender(mochaConnection);
    eventBus.register(packetSender);
    return packetSender;
  }
}
