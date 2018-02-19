package mocha.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.stage.Stage;
import mocha.client.net.Connection;
import mocha.client.net.PacketListener;

@SpringBootApplication
public class Main extends Application {

  private static String[] args;

  private static ExecutorService threadPool = Executors.newCachedThreadPool();

  public static void main(String[] args) {
    Main.args = args;
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    ApplicationContext context = SpringApplication.run(Main.class, args);

    Socket socket = getSocket();
    Connection connection = new Connection(socket);
    threadPool.submit(new PacketListener(connection));
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
