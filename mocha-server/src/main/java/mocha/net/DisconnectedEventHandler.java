package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.player.PlayerService;
import mocha.net.event.DisconnectedEvent;

@Component
public class DisconnectedEventHandler implements MochaEventHandler<DisconnectedEvent> {

  private PlayerService playerService;
  private Logger log = LoggerFactory.getLogger(DisconnectedEventHandler.class);

  @Inject
  public DisconnectedEventHandler(PlayerService playerService) {
    this.playerService = playerService;
  }

  @Subscribe
  public void handle(DisconnectedEvent disconnectedEvent) {
//    log.info(disconnectedEvent.toString());
//    playerService.removePlayer(disconnectedEvent.getSenderId());
  }
}