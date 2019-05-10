package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.ClientPacketResolver;
import mocha.client.event.ClientEventBus;
import mocha.game.event.MochaEventHandler;
import mocha.game.world.Location;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketSender;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.task.TaskService;

@Component
public class ConnectedEventHandler implements MochaEventHandler<ConnectedEvent> {

  private ClientEventBus eventBus;
  private TaskService taskService;
  private PacketSenderFactory packetSenderFactory;
  private ClientPacketResolver packetResolver;
  private MochaConnectionProvider mochaConnectionProvider;

  @Inject
  public ConnectedEventHandler(
      ClientEventBus eventBus,
      TaskService taskService,
      PacketSenderFactory packetSenderFactory,
      @Lazy ClientPacketResolver packetResolver,
      MochaConnectionProvider mochaConnectionProvider
  ) {
    this.eventBus = eventBus;
    this.taskService = taskService;
    this.packetSenderFactory = packetSenderFactory;
    this.packetResolver = packetResolver;
    this.mochaConnectionProvider = mochaConnectionProvider;
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    MochaConnection mochaConnection = connectedEvent.getMochaConnection();
    mochaConnectionProvider.set(mochaConnection);
    PacketSender packetSender = packetSenderFactory.newPacketSender(mochaConnection);
    PacketListener packetListener = new PacketListener(eventBus, mochaConnection, -1, packetResolver);
    eventBus.register(packetSender);
    eventBus.register(packetListener);
    taskService.submit(packetListener);
    sendLoginRequest();

    requestChunkData(-1, -1);
    requestChunkData(-1, 0);
    requestChunkData(0, -1);
    requestChunkData(0, 0);
  }

  private void sendLoginRequest() {
    mochaConnectionProvider.get().sendLoginRequest("link");
  }

  private void requestChunkData(int x, int y) {
    Location location = new Location(x, y);
    mochaConnectionProvider.get().requestChunkAt(location);
    mochaConnectionProvider.get().requestEntitiesInChunk(location);
  }
}