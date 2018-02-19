package mocha;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.GameLoop;
//import mocha.mocha.gfx.RenderLoop;
import mocha.server.Server;

@Component
public class Mocha {

  @Inject
  private GameLoop gameLoop;
//  @Inject
//  private RenderLoop renderLoop;
  @Inject
  private Server server;

  @PostConstruct
  public void init() {
    gameLoop.start();
//    renderLoop.start();
    server.start();
  }

}
