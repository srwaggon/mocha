package mocha.game;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.server.event.ServerEventBus;

@Component
public class ServerGameLogic implements GameLogic {

  @Inject
  public ServerGameLogic(
      ServerEventBus eventBus,
      List<MochaEventHandler> mochaEventHandlers,
      List<CommandHandler> commandHandlers
  ) {
    mochaEventHandlers.forEach(eventBus::register);
    commandHandlers.forEach(eventBus::register);
  }

}
