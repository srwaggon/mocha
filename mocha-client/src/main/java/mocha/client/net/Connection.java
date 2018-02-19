package mocha.client.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
  private boolean isConnected;
  private Socket socket;
  private Scanner in;
  private PrintWriter out;

  public Connection(Socket socket) {
    try {
      this.socket = socket;
      this.in = new Scanner(new InputStreamReader(socket.getInputStream()));
      this.out = new PrintWriter(socket.getOutputStream());
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

    isConnected = true;
  }

  public boolean isConnected() {
    return isConnected;
  }

  public boolean hasLine() {
    return in.hasNextLine();
  }

  public String readLine() {
    return in.nextLine();
  }

  public void disconnect() {
    isConnected = false;
    in.close();
    out.close();
    try {
      socket.close();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public void send(String message) {
    out.println(message);
    out.flush();
  }
}
