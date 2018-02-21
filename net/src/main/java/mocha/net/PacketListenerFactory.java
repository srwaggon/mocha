package mocha.net;

import mocha.net.packet.PacketFactory;

public class PacketListenerFactory {

  private PacketFactory packetFactory;

  public PacketListenerFactory(PacketFactory packetFactory) {
    this.packetFactory = packetFactory;
  }

  public PacketListener newPacketListenerFactory(Connection connection) {
    return new PacketListener(connection, packetFactory);
  }
}
