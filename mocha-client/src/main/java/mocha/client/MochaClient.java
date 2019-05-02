package mocha.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.gfx.RenderLoop;
import mocha.game.GameLogic;
import mocha.game.GameLoop;
import mocha.game.RuleService;
import mocha.shared.task.TaskService;

@Component
public class MochaClient {

  @Inject
  private ClientEventBus clientEventBus;
  @Inject
  private TaskService taskService;
  @Inject
  private GameLoop gameLoop;
  @Inject
  private RuleService ruleService;
  @Inject
  private NetworkClient networkClient;
  @Inject
  private RenderLoop renderLoop;
  @Inject
  private GameLogic gameLogic;
  @Inject
  private ClientPacketResolver clientPacketHandler;

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @PostConstruct
  public void init() {
    if (isOnline) {
      taskService.submit(clientPacketHandler);
      taskService.submit(networkClient);
    }

    taskService.submit(() -> gameLoop.start());
    gameLoop.submit(ruleService);
    renderLoop.start();
  }

}
