package mocha.game;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.game.world.Direction;

@Component
@Slf4j
public class GameEventHandler {

  @Inject
  private EventBus eventBus;

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handleMapChange(MapChangeEvent mapChangeEvent) {
    log.debug("handling map change");
  }
}

interface MapChangeEvent {

  Direction getDirection();

}

class NorthMapChangeEvent implements MapChangeEvent {

  @Override
  public Direction getDirection() {
    return Direction.NORTH;
  }
}