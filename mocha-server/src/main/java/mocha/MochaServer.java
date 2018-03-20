package mocha;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.ServerGameLogic;
import mocha.server.Server;
import mocha.server.event.ServerEventBus;
import mocha.shared.task.TaskService;

@Component
public class MochaServer {

  @Inject
  private ServerEventBus serverEventBus;
  @Inject
  private TaskService taskService;
  @Inject
  private GameLoop gameLoop;
  @Inject
  private Game game;
  @Inject
  private Server server;
  @Inject
  private ServerGameLogic serverGameLogic;

  @PostConstruct
  public void init() {
    serverEventBus.register(taskService);
    serverEventBus.register(serverGameLogic);
    taskService.submit(server);
    gameLoop.start();
    gameLoop.submit(game);
  }

}
