package mocha.net.packet;

import java.net.Socket;

import mocha.net.Connection;
import mocha.net.exception.DisconnectedException;

class PacketConnection extends Connection {

  PacketConnection(Socket socket) {
    super(socket);
  }

  void send(Packet packet) {
    send(packet.construct());
  }

  Packet readPacket() throws DisconnectedException {
    return PacketType.resolve(new UnknownPacket(readLine()));
  }
}
