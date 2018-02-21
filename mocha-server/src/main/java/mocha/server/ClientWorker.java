package mocha.server;

import com.google.common.eventbus.Subscribe;

import mocha.net.PacketHandler;
import mocha.net.packet.Packet;

public class ClientWorker implements PacketHandler {
  @Override
  @Subscribe
  public void handlePacket(Packet packet) {
    System.out.println(packet.construct());
  }
}
