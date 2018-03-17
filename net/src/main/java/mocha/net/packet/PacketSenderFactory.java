package mocha.net.packet;

import com.google.common.eventbus.EventBus;

public class PacketSenderFactory {

  private EventBus eventBus;

  public PacketSenderFactory(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public PacketSender newPacketSender(PacketConnection packetConnection) {
    PacketSender packetSender = new PacketSender(packetConnection);
    eventBus.register(packetSender);
    return packetSender;
  }
}
