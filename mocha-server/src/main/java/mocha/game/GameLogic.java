package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.net.event.ConnectedEvent;
import mocha.net.event.NetworkedMochaEventBus;

@Component
public class GameLogic {

  @Inject
  private NetworkedMochaEventBus networkedMochaEventBus;

  @Inject
  private Registry<Player> playerRegistry;

  @PostConstruct
  public void init() {
    networkedMochaEventBus.register(this);
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {

  }
}
