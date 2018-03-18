package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.net.event.ConnectedEvent;
import mocha.net.event.DisconnectedEvent;
import mocha.net.event.NetworkedMochaEventBus;

@Slf4j
@Component
public class ServerGameLogic {

  @Inject
  private NetworkedMochaEventBus eventBus;

  @Inject
  private PlayerFactory playerFactory;

  @Inject
  private Registry<Player> playerRegistry;

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
    Player player = playerFactory.newPlayer(connectedEvent.getMochaConnection());
    playerRegistry.add(player);
    Entity newEntity = player.getEntity();
    game.add(newEntity);
    eventBus.postAddEntityEvent(newEntity);
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
    log.info(disconnectedEvent.toString());
  }
}
