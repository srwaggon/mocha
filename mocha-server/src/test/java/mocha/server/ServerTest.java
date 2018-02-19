package mocha.server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import mocha.net.Connection;

import static org.junit.Assert.assertEquals;

public class ServerTest {

  private Server testObject;

  private static int port = 8026;

  private void startServer(int port) throws IOException {
    testObject = new Server(port);
    testObject.start();
  }

  @Before
  public void setUp() {
    port++;
  }

  @Test
  public void run_AcceptsAnIncomingConnection() throws IOException {
    startServer(port);

    Connection connection = new Connection(new Socket("localhost", port));
    connection.send("echo");

    assertEquals("echo", connection.readLine());
  }

  @Test
  public void run_AcceptsMultipleIncomingConnections() throws IOException {
    startServer(port);
    Connection connection1 = new Connection(new Socket("localhost", port));
    Connection connection2 = new Connection(new Socket("localhost", port));
    Connection connection3 = new Connection(new Socket("localhost", port));

    connection1.send("connection1");
    connection2.send("connection2");
    connection3.send("connection3");
    connection2.send("connection2 again");

    assertEquals("connection1", connection1.readLine());
    assertEquals("connection2", connection2.readLine());
    assertEquals("connection3", connection3.readLine());
    assertEquals("connection2 again", connection2.readLine());
  }

}