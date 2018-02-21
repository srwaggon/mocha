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
import mocha.game.world.Location;
import mocha.net.Connection;
import mocha.net.MochaConnection;
import mocha.net.PacketListener;
import mocha.net.PacketListenerFactory;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

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

    PacketListenerFactory packetListenerFactory = context.getBean(PacketListenerFactory.class);

    Socket socket = getSocket();
    Connection connection = new Connection(socket);
    MochaConnection mochaConnection = new MochaConnection(connection);
    PacketListener packetListener = packetListenerFactory.newPacketListener(mochaConnection);
    threadPool.submit(packetListener);

    threadPool.submit(() -> {
      while (mochaConnection.isConnected()) {
//        mochaConnection.sendPacket(new GlobalMessagePacket("This message goes to the server."));
        mochaConnection.sendPacket(new ChunkRequestPacket(new Location(0,0)));
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("Lost connection with " + mochaConnection);
    });

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
