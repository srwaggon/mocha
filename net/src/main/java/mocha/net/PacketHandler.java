package mocha.net;

import mocha.net.packet.Packet;

public interface PacketHandler {

  void handlePacket(Packet packet);

}
