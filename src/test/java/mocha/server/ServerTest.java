package mocha.server;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import mocha.net.Connection;

import static org.junit.Assert.*;

public class ServerTest {

  private Server testObject;

  private int port = 8026;

  @Before
  public void setUp() throws Exception {
    testObject = new Server(port);
  }

  @Test
  public void run_AcceptsIncomingConnections() throws IOException {
    new Thread() {
      @Override
      public void run() {
        testObject.run();
      }
    }.start();

    Connection connection = new Connection(new Socket("localhost", port));
    connection.send("echo");

    assertEquals("echo", connection.readLine());
  }
}