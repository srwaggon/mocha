package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.server.event.ServerEventBus;

@Component
public class ServerGameLogic {

  private Logger log = LoggerFactory.getLogger(ServerGameLogic.class);

  @Inject
  private ServerEventBus eventBus;

  @Inject
  private Game game;

  @Inject
  private PlayerFactory playerFactory;

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());
    game.addPlayer(playerFactory.newNetworkPlayer(connectedEvent.getMochaConnection()));
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
    game.removePlayer(disconnectedEvent.getSenderId());
  }

}
