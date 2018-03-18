package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.game.world.entity.EntityFactory;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.server.ClientWorker;
import mocha.server.ClientWorkerFactory;

@Slf4j
@Component
public class ServerGameLogic {

  @Inject
  private NetworkedMochaEventBus eventBus;

  @Inject
  private ClientWorkerFactory clientWorkerFactory;

  @Inject
  private Registry<ClientWorker> clientWorkerRegistry;

  @Inject
  private Game game;

  @Inject
  private EntityFactory entityFactory;

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());
    MochaConnection mochaConnection = connectedEvent.getMochaConnection();

    ClientWorker clientWorker = clientWorkerFactory.newClientWorker(mochaConnection);
    clientWorkerRegistry.add(clientWorker);
    game.add(clientWorker.getEntity());
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
  }
}
