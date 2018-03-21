package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.server.event.ServerEventBus;

@Slf4j
@Component
public class ServerGameLogic {

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
