package mocha.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

  private ServerSocket server;
  private ExecutorService threadPool = Executors.newCachedThreadPool();

  public Server(int port) throws IOException {
    server = new ServerSocket(port);
  }

  public void run() {
    while (true) {
      try {
        System.out.println("Awaiting connections...");
          acceptConnection(server.accept());

      } catch (IOException ioexception) {
        ioexception.printStackTrace();
        System.exit(-1);
      }
    }
  }

  private void acceptConnection(Socket socket) {
    threadPool.submit((Runnable) () -> {
      System.out.println("Receiving connection from " + socket.getInetAddress().toString());

      Connection connection = new Connection(socket);

      while(connection.isConnected()) {
        if (connection.hasLine()) {
          connection.send(connection.readLine());
        }
      }
    });

  }

  public void shutdown() throws IOException {
    server.close();
  }
}
