package mocha;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.Client;
import mocha.client.ClientPacketHandler;
import mocha.client.event.ClientEventBus;
import mocha.client.gfx.RenderLoop;
import mocha.game.ClientGameLogic;
import mocha.game.Game;
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
  private Client client;
  @Inject
  private RenderLoop renderLoop;
  @Inject
  private ClientGameLogic clientGameLogic;
  @Inject
  private ClientPacketHandler clientPacketHandler;

  @PostConstruct
  public void init() {
    clientEventBus.register(taskService);
    clientEventBus.register(clientGameLogic);
    clientEventBus.register(clientPacketHandler);
    taskService.submit(() -> gameLoop.start());
    taskService.submit(clientPacketHandler);
    taskService.submit(client);
    gameLoop.submit(game);
    renderLoop.start();
  }

}
