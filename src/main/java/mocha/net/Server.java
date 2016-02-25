package mocha.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mocha.net.Connection;

public class Server {

  ServerSocket server;

  public Server(int port) throws IOException {
    server = new ServerSocket(port);
  }

  public void run() {
    try {
      System.out.println("Awaiting connections...");

      acceptConnection(server.accept());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void acceptConnection(Socket socket) {
    System.out.println("Receiving connection from " + socket.getInetAddress().toString());

    Connection connection = new Connection(socket);

    while(connection.hasLine()) {
      connection.send(connection.readLine());
    }
  }
}
