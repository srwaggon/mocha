package mocha.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.net.Connection;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketConnection;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSenderFactory;

@Component
@Slf4j
public class Server implements Runnable {

  private ServerSocket server;

  @Inject
  private PacketListenerFactory packetListenerFactory;
  @Inject
  private NetworkedMochaEventBus eventBus;

  @Inject
  private ServerPacketHandlerFactory serverPacketHandlerFactory;

  @Inject
  private ExecutorService executorService;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  Server(@Value("${mocha.server.port}") int port) throws IOException {
    log.info("Staring server on port {}", port);
    server = new ServerSocket(port);
  }

  public void start() {
    executorService.submit(this);
  }

  @Override
  public void run() {
    awaitConnections();
  }

  private void awaitConnections() {
    while (true) {
      try {
        log.info("Awaiting connections...");
        acceptConnection(server.accept());
        nap();
      } catch (IOException ioexception) {
        log.error("IOException while awaiting connections: ", ioexception);
      }
    }
  }

  private void nap() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void acceptConnection(Socket socket) {
    log.info("Receiving connection from " + socket.getInetAddress().toString());
    Connection connection = new Connection(socket);
    PacketConnection packetConnection = new PacketConnection(connection);
    MochaConnection mochaConnection = new MochaConnection(packetConnection, packetFactory);

    eventBus.connected(mochaConnection);

    serverPacketHandlerFactory.newServerPacketHandler(mochaConnection);

    PacketListener packetListener = packetListenerFactory.newPacketListener(packetConnection);
    executorService.submit(packetListener);
  }

}
