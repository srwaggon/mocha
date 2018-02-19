package mocha.net;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Server implements Runnable {

  private ServerSocket server;
  private ExecutorService threadPool = Executors.newCachedThreadPool();

  @Inject
  private PacketListenerFactory packetListenerFactory;

  @Inject
  Server(@Value("${mocha.server.port}") int port) throws IOException {
    log.info("Staring server on port {}", port);
    server = new ServerSocket(port);
  }

  public void start() {
    threadPool.submit(this);
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
    PacketListener packetListener = packetListenerFactory.newPacketListenerFactory(connection);
    threadPool.submit(packetListener);
  }

  public void shutdown() throws IOException {
    threadPool.shutdown();
    server.close();
  }
}
