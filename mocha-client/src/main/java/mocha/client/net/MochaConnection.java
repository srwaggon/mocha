package mocha.client.net;

import mocha.client.net.packet.Packet;
import mocha.client.net.packet.PacketType;
import mocha.client.net.packet.UnknownPacket;

public class MochaConnection {

  private final Connection connection;

  MochaConnection(Connection connection) {
    this.connection = connection;
  }

  public void sendPacket(Packet packet) {
    connection.send(packet.construct());
  }

  public boolean hasPacket() {
    return connection.hasLine();
  }

  public Packet readPacket() {
    return PacketType.resolve(new UnknownPacket(connection.readLine()));
  }
}
