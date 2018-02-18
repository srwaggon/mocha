package mocha.net;

public class PacketListener implements Runnable {

  private final Connection connection;

  PacketListener(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    while (connection.isConnected()) {
      if (connection.hasLine()) {
        connection.send(connection.readLine());
      }
    }
  }
}
