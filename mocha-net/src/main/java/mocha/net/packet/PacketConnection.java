package mocha.net.packet;

import java.net.Socket;

import mocha.net.Connection;
import mocha.net.exception.DisconnectedException;

public class PacketConnection extends Connection {

  public PacketConnection(Socket socket) {
    super(socket);
  }

  public void sendPacket(Packet packet) {
    send(packet.construct());
  }

  public boolean hasPacket() {
    return hasLine();
  }

  public Packet readPacket() throws DisconnectedException {
    return PacketType.resolve(new UnknownPacket(readLine()));
  }
}
