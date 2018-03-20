package mocha.client;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.client.event.ClientEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;

@Component
@Slf4j
public class Client implements Runnable {

  @Inject
  private ClientEventBus eventBus;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private ClientPacketHandler clientPacketHandler;

  @Override
  public void run() {
    eventBus.postConnectedEvent(getMochaConnection());
  }

  private MochaConnection getMochaConnection() {
    return new MochaConnection(getSocket(), packetFactory);
  }

  private Socket getSocket() {
    try {
      return new Socket(getLocalHost(), 8026);
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
