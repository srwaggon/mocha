package mocha.client;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.game.GameLoop;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.task.TaskService;

@Component
@Slf4j
public class Client implements Runnable {

  @Inject
  private PacketListenerFactory packetListenerFactory;

  @Inject
  private PacketSenderFactory packetSenderFactory;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private TaskService taskService;

  @Inject
  private ClientPacketHandler clientPacketHandler;

  @Inject
  private GameLoop gameLoop;

  @PostConstruct
  public void init() {
    taskService.submit(() -> gameLoop.start()); // todo: must this be submitted?
    taskService.submit(this);
  }

  @Override
  public void run() {
    MochaConnection connection = getMochaConnection();
    packetSenderFactory.newPacketSender(connection);
    taskService.submit(packetListenerFactory.newPacketListener(connection, -1));
  }

  private MochaConnection getMochaConnection() {
    return new MochaConnection(getSocket(), packetFactory);
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
