package mocha.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import mocha.net.exception.DisconnectedException;

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

  public String readLine() throws DisconnectedException {
    try {
      String nextLine = in.nextLine();
      System.out.println("reading: " + nextLine);
      return nextLine;
    } catch (NoSuchElementException e) {
      this.disconnect();
      throw new DisconnectedException(e);
    }
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
    System.out.println("sending: " +message);
    out.println(message);
    out.flush();
  }
}
