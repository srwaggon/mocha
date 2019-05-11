package mocha.server;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.GameLoop;
import mocha.game.RuleService;
import mocha.server.event.ServerEventBus;
import mocha.shared.task.TaskService;

@Component
public class MochaServer {

  private ServerEventBus serverEventBus;
  private TaskService taskService;
  private GameLoop gameLoop;
  private RuleService ruleService;
  private Server server;

  @Inject
  public MochaServer(
      ServerEventBus serverEventBus,
      TaskService taskService,
      GameLoop gameLoop,
      RuleService ruleService,
      Server server
  ) {
    this.serverEventBus = serverEventBus;
    this.taskService = taskService;
    this.gameLoop = gameLoop;
    this.ruleService = ruleService;
    this.server = server;
  }

  @PostConstruct
  public void init() {
    serverEventBus.register(taskService);
    taskService.submit(server);
    gameLoop.start();
    gameLoop.submit(ruleService);
  }

}
