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
public class Server {

  private ServerSocket server;
  private ExecutorService threadPool = Executors.newCachedThreadPool();

  @Inject
  Server(@Value("${mocha.server.port}") int port) throws IOException {
    server = new ServerSocket(port);
    log.info("Server started on port {}", port);
  }

  void run() {
    while (true) {
      try {
        log.info("Awaiting connections...");
        acceptConnection(server.accept());

      } catch (IOException ioexception) {
        log.error("IOException while awaiting connections: ", ioexception);
        ioexception.printStackTrace();
        System.exit(-1);
      }
    }
  }

  private void acceptConnection(Socket socket) {
    log.info("Receiving connection from " + socket.getInetAddress().toString());
    threadPool.submit(new PacketListener(new Connection(socket)));
  }

  public void shutdown() throws IOException {
    threadPool.shutdown();
    server.close();
  }
}
