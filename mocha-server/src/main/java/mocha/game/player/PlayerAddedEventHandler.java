package mocha.game.player;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.player.event.PlayerAddedEvent;

@Component
public class PlayerAddedEventHandler implements MochaEventHandler<PlayerAddedEvent> {

  @Inject
  public PlayerAddedEventHandler() {
  }

  @Subscribe
  public void handle(PlayerAddedEvent playerAddedEvent) {

  }
}