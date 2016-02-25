package mocha.net;

import mocha.net.packet.Packet;
import mocha.net.packet.PacketCode;
import mocha.net.packet.UnknownPacket;

public class MochaConnection {

  private final Connection connection;

  public MochaConnection(Connection connection) {
    this.connection = connection;
  }

  public void sendPacket(Packet packet) {
    connection.send(packet.construct());
  }

  public boolean hasPacket() {
    return connection.hasLine();
  }

  public Packet readPacket() {
    return PacketCode.resolve(new UnknownPacket(connection.readLine()));
  }
}
