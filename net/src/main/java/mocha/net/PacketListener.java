package mocha.net;

import mocha.net.packet.ChunkPacket;
import mocha.net.packet.PacketFactory;

public class PacketListener implements Runnable {

  private final Connection connection;
  private PacketFactory packetFactory;

  public PacketListener(Connection connection, PacketFactory packetFactory) {
    this.connection = connection;
    this.packetFactory = packetFactory;
  }

  @Override
  public void run() {
    while (connection.isConnected()) {
      if (connection.hasLine()) {
        System.out.println(connection.readLine());
      }
      ChunkPacket chunkPacket = packetFactory.newChunkPacket();
      connection.send(chunkPacket.construct());
    }
  }
}
