package mocha.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.inject.Inject;

import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;
import mocha.shared.task.SleepyRunnable;

@Component
public class Server implements SleepyRunnable {

  private Logger log = LoggerFactory.getLogger(Server.class);

  private ServerSocket server;
  private ServerEventBus eventBus;

  @Inject
  Server(
      @Value("${mocha.server.port}") int port,
      ServerEventBus eventBus
  ) throws IOException {
    this.eventBus = eventBus;
    log.info("Staring server on port {}", port);
    server = new ServerSocket(port);
  }

  @Override
  public void run() {
    awaitConnections();
  }

  private void awaitConnections() {
    //noinspection InfiniteLoopStatement
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

  private void acceptConnection(Socket socket) {
    log.info("Receiving connection from " + socket.getInetAddress().toString());
    eventBus.postConnectedEvent(new MochaConnection(socket));
  }

}
