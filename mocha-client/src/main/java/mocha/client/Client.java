package mocha.client;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.client.gfx.RenderLoop;
import mocha.net.Connection;
import mocha.net.MochaConnection;
import mocha.net.PacketListener;
import mocha.net.PacketListenerFactory;
import mocha.net.PacketSenderFactory;
import mocha.net.SendPacket;
import mocha.net.packet.PacketFactory;

@Component
@Slf4j
public class Client implements Runnable {

  @Inject
  private EventBus eventBus;

  @Inject
  private PacketListenerFactory packetListenerFactory;

  @Inject
  private PacketSenderFactory packetSenderFactory;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private ExecutorService executorService;

  @Inject
  private ClientPacketHandler clientPacketHandler;

  @Inject
  private RenderLoop renderLoop;

  @PostConstruct
  public void init() {
    executorService.submit(this);
    renderLoop.start();
  }

  @Override
  public void run() {
    MochaConnection mochaConnection = getMochaConnection();
    packetSenderFactory.newPacketSender(mochaConnection);

    PacketListener packetListener = packetListenerFactory.newPacketListener(mochaConnection);
    executorService.submit(packetListener);
    executorService.submit(() -> execute(mochaConnection));
  }

  private MochaConnection getMochaConnection() {
    Socket socket = getSocket();
    Connection connection = new Connection(socket);
    return new MochaConnection(connection);
  }

  private void execute(MochaConnection mochaConnection) {
    eventBus.post(new SendPacket(packetFactory.newChunkRequestPacket()));

    while (mochaConnection.isConnected()) {
      nap();
    }
    log.info("Lost connection with " + mochaConnection);
  }

  private void nap() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException interruptedException) {
      log.error("Client .. something interrupted", interruptedException);
    }
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
