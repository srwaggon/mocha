package mocha.net;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.net.packet.PacketFactory;

@Component
public class PacketListenerFactory {

  @Inject
  private PacketFactory packetFactory;

  PacketListener newPacketListenerFactory(Connection connection) {
    return new PacketListener(connection, packetFactory);
  }
}
