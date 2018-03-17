package mocha.net.packet;

import mocha.net.Connection;
import mocha.net.exception.DisconnectedException;

public class PacketConnection {

  private final Connection connection;

  public PacketConnection(Connection connection) {
    this.connection = connection;
  }

  public boolean isConnected() {
    return connection.isConnected();
  }

  public void sendPacket(Packet packet) {
    connection.send(packet.construct());
  }

  public boolean hasPacket() {
    return connection.hasLine();
  }

  public Packet readPacket() throws DisconnectedException {
    return PacketType.resolve(new UnknownPacket(connection.readLine()));
  }
}
