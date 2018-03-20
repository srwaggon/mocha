package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.game.world.entity.EntityFactory;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.server.ClientWorker;
import mocha.server.ClientWorkerFactory;
import mocha.server.event.ClientConnectedEvent;
import mocha.server.event.ServerEventBus;

@Slf4j
@Component
public class ServerGameLogic {

  @Inject
  private ServerEventBus eventBus;

  @Inject
  private ClientWorkerFactory clientWorkerFactory;

  @Inject
  private Registry<ClientWorker> clientWorkerRegistry;

  @Inject
  private Game game;

  @Inject
  private EntityFactory entityFactory;

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());
    eventBus.postClientConnectedEvent(clientWorkerFactory.newClientWorker(connectedEvent.getMochaConnection()));
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
    clientWorkerRegistry.get(disconnectedEvent.getSenderId())
        .ifPresent(this::removeClient);

  }

  private void removeClient(ClientWorker clientWorker) {
    game.remove(clientWorker.getEntity());

    clientWorkerRegistry.remove(clientWorker);
  }

  @Subscribe
  public void handle(ClientConnectedEvent clientConnectedEvent) {
    ClientWorker clientWorker = clientConnectedEvent.getClientWorker();
    clientWorkerRegistry.add(clientWorker);
    game.add(clientWorker.getEntity());
  }
}
