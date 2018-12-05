package mocha;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.GameLoop;
import mocha.game.RuleService;
import mocha.game.ServerGameLogic;
import mocha.server.Server;
import mocha.server.event.ServerEventBus;
import mocha.shared.task.TaskService;

@Component
public class MochaServer {

  private ServerEventBus serverEventBus;
  private TaskService taskService;
  private GameLoop gameLoop;
  private RuleService ruleService;
  private Server server;
  private ServerGameLogic serverGameLogic;

  @Inject
  public MochaServer(
      ServerEventBus serverEventBus,
      TaskService taskService,
      GameLoop gameLoop,
      RuleService ruleService,
      Server server,
      ServerGameLogic serverGameLogic
  ) {
    this.serverEventBus = serverEventBus;
    this.taskService = taskService;
    this.gameLoop = gameLoop;
    this.ruleService = ruleService;
    this.server = server;
    this.serverGameLogic = serverGameLogic;
  }

  @PostConstruct
  public void init() {
    serverEventBus.register(taskService);
    serverEventBus.register(serverGameLogic);
    taskService.submit(server);
    gameLoop.start();
    gameLoop.submit(ruleService);
  }

}
