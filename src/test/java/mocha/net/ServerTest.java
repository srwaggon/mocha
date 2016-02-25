package mocha.net;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

  private Server testObject;

  private static int port = 8026;

  private void startServer(int port) {
    new Thread() {
      @Override
      public void run() {
        try {
          testObject = new Server(port);
        } catch (IOException e) {
          e.printStackTrace();
        }
        testObject.run();
      }
    }.start();
  }

  @Test
  public void run_AcceptsAnIncomingConnection() throws IOException {
    int port = ServerTest.port++;
    startServer(port);

    Connection connection = new Connection(new Socket("localhost", port));
    connection.send("echo");

    assertEquals("echo", connection.readLine());
  }

  @Test
  public void run_AcceptsMultipleIncomingConnections() throws IOException {
    int port = ServerTest.port++;
    startServer(port);

    Connection connection1 = new Connection(new Socket("localhost", port));
    Connection connection2 = new Connection(new Socket("localhost", port));

    connection1.send("connection1");
    connection2.send("connection2");

    assertEquals("connection1", connection1.readLine());
    assertEquals("connection2", connection2.readLine());
  }
}