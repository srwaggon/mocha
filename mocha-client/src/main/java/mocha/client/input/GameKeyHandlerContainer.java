package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.GameKeyEvent;

@Component
public class GameKeyHandlerContainer {

  private ClientEventBus eventBus;
  private List<GameKeyHandler> gameKeyHandlers;

  @Inject
  public GameKeyHandlerContainer(
      ClientEventBus eventBus,
      List<GameKeyHandler> gameKeyHandlers
  ) {
    this.eventBus = eventBus;
    this.gameKeyHandlers = gameKeyHandlers;
  }

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handle(GameKeyEvent gameKeyEvent) {
    gameKeyHandlers.forEach(gameKeyHandler -> gameKeyHandler.handle(gameKeyEvent));
  }

}
