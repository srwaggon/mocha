package mocha.server;

import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.net.Connection;
import mocha.net.MochaConnection;
import mocha.net.PacketListener;
import mocha.net.PacketListenerFactory;

@Component
@Slf4j
public class Server implements Runnable {

  private ServerSocket server;

  @Inject
  private PacketListenerFactory packetListenerFactory;
  @Inject
  private EventBus eventBus;

  @Inject
  private ServerPacketHandlerFactory serverPacketHandlerFactory;

  @Inject
  private ExecutorService executorService;

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
      } catch (IOException ioexception) {
        log.error("IOException while awaiting connections: ", ioexception);
      }
    }
  }

  private void acceptConnection(Socket socket) {
    log.info("Receiving connection from " + socket.getInetAddress().toString());
    Connection connection = new Connection(socket);
    MochaConnection mochaConnection = new MochaConnection(connection);

    serverPacketHandlerFactory.newServerPacketHandler(mochaConnection);

    PacketListener packetListener = packetListenerFactory.newPacketListener(mochaConnection);
    executorService.submit(packetListener);
  }

}
