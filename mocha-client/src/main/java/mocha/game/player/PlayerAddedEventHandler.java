package mocha.game.player;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.player.event.PlayerAddedEvent;
import mocha.net.MochaConnectionProvider;

@Component
public class PlayerAddedEventHandler implements MochaEventHandler<PlayerAddedEvent> {

  private MochaConnectionProvider mochaConnectionProvider;

  @Inject
  public PlayerAddedEventHandler(MochaConnectionProvider mochaConnectionProvider) {
    this.mochaConnectionProvider = mochaConnectionProvider;
  }

  @Subscribe
  public void handle(PlayerAddedEvent playerAddedEvent) {
    int playerId = playerAddedEvent.getPlayer().getId();
    mochaConnectionProvider.get().requestEntitiesByPlayerId(playerId);
  }
}