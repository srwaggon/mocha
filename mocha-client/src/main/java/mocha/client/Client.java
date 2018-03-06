package mocha.client;

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
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.net.Connection;
import mocha.net.MochaConnection;
import mocha.net.PacketListener;
import mocha.net.PacketListenerFactory;
import mocha.net.PacketSenderFactory;
import mocha.net.SendPacketEvent;
import mocha.net.packet.PacketFactory;

@Component
@Slf4j
public class Client implements Runnable {

  @Inject
  private MochaClientEventBus eventBus;

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
    Location chunk0 = new Location(-Chunk.getWidth(), -Chunk.getHeight());
    Location chunk1 = new Location(-Chunk.getWidth(), 0);
    Location chunk2 = new Location(0, -Chunk.getHeight());
    Location chunk3 = new Location(0, 0);

    eventBus.post(new SendPacketEvent(packetFactory.newChunkRequestPacket(chunk0)));
    eventBus.post(new SendPacketEvent(packetFactory.newChunkRequestPacket(chunk1)));
    eventBus.post(new SendPacketEvent(packetFactory.newChunkRequestPacket(chunk2)));
    eventBus.post(new SendPacketEvent(packetFactory.newChunkRequestPacket(chunk3)));

    eventBus.post(new SendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(chunk0)));
    eventBus.post(new SendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(chunk1)));
    eventBus.post(new SendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(chunk2)));
    eventBus.post(new SendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(chunk3)));

    while (mochaConnection.isConnected()) {
      nap();
    }
    log.info("Lost connection with " + mochaConnection);
  }

  private void nap() {
    try {
      Thread.sleep(10);
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
