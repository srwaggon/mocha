package mocha.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;

@Component
public class NetworkClient implements Runnable {

  @Inject
  private ClientEventBus eventBus;

  @Inject
  private PacketFactory packetFactory;

  @Value("${mocha.server.port}")
  private int port;

  @Value("${mocha.server.host}")
  private String host;

  @Override
  public void run() {
    eventBus.postConnectedEvent(getMochaConnection());
  }

  private MochaConnection getMochaConnection() {
    return new MochaConnection(getSocket(), packetFactory);
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
