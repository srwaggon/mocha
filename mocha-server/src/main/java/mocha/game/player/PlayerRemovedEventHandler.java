package mocha.game.player;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.player.event.PlayerRemovedEvent;

@Component
public class PlayerRemovedEventHandler implements MochaEventHandler<PlayerRemovedEvent> {

  @Inject
  public PlayerRemovedEventHandler() {
  }

  @Subscribe
  public void handle(PlayerRemovedEvent playerRemovedEvent) {

  }
}