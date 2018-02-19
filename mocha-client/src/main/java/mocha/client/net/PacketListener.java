package mocha.client.net;

public class PacketListener implements Runnable {

  private final Connection connection;

  public PacketListener(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    connection.send("Hey, I'm connected.");

    while (connection.isConnected()) {
      if (connection.hasLine()) {
        String message = connection.readLine();
        System.out.println(message);
      }
    }
  }
}
