package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.GameKeyEvent;

@Component
public class GameKeyHandlerContainer {

  private final GameKeyHandler[] gameKeyHandlers;
  private ClientEventBus eventBus;

  @Inject
  public GameKeyHandlerContainer(
      ClientEventBus eventBus,
      GameKeyHandler[] gameKeyHandlers
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
    Arrays.stream(gameKeyHandlers)
        .forEach(gameKeyHandler -> gameKeyHandler.handle(gameKeyEvent));
  }

}
