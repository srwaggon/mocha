package mocha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.ClientPacketHandler;
import mocha.client.NetworkClient;
import mocha.client.event.ClientEventBus;
import mocha.client.gfx.RenderLoop;
import mocha.game.Game;
import mocha.game.GameLogic;
import mocha.game.GameLoop;
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
  private Game game;
  @Inject
  private NetworkClient networkClient;
  @Inject
  private RenderLoop renderLoop;
  @Inject
  private GameLogic gameLogic;
  @Inject
  private ClientPacketHandler clientPacketHandler;

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @PostConstruct
  public void init() {
    if (isOnline) {
      taskService.submit(clientPacketHandler);
      taskService.submit(networkClient);
    }

    taskService.submit(() -> gameLoop.start());
    gameLoop.submit(game);
    renderLoop.start();
  }

}
