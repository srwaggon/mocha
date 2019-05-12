package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.ClientPacketResolver;
import mocha.client.event.ClientEventBus;
import mocha.game.event.MochaEventHandler;
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

    sendCreateAccountRequest();
    sendLoginRequest();
  }

  private void sendCreateAccountRequest() {
    getMochaConnection().sendCreateAccountRequest("link", "link@hyrule.com");
  }

  private void sendLoginRequest() {
    getMochaConnection().sendLoginRequest("link");
  }

  private MochaConnection getMochaConnection() {
    return mochaConnectionProvider.get();
  }
}