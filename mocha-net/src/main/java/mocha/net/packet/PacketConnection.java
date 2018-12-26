package mocha.net.packet;

import java.net.Socket;

import mocha.net.Connection;
import mocha.net.exception.DisconnectedException;

public class PacketConnection extends Connection {

  PacketConnection(Socket socket) {
    super(socket);
  }

  void sendPacket(Packet packet) {
    send(packet);
  }

  void send(Packet packet) {
    send(packet.construct());
  }

  public boolean hasPacket() {
    return hasLine();
  }

  Packet readPacket() throws DisconnectedException {
    return PacketType.resolve(new UnknownPacket(readLine()));
  }
}
