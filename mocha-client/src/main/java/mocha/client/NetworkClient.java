package mocha.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import mocha.client.event.ClientEventBus;
import mocha.net.packet.MochaConnection;

@Component
public class NetworkClient {

  private ClientEventBus clientEventBus;
  private int port;
  private String host;

  public NetworkClient(
      ClientEventBus clientEventBus,
      @Value("${mocha.server.host}") String host,
      @Value("${mocha.server.port}") int port
  ) {
    this.clientEventBus = clientEventBus;
    this.port = port;
    this.host = host;
  }

  void connectToServer() {
    clientEventBus.postConnectedEvent(getMochaConnection());
  }

  private MochaConnection getMochaConnection() {
    return new MochaConnection(getSocket());
  }

  private Socket getSocket() {
    try {
      return new Socket(host, port);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private InetAddress getLocalHost() {
    try {
      return InetAddress.getLocalHost();
    } catch (UnknownHostException exception) {
      throw new RuntimeException(exception);
    }
  }
}
