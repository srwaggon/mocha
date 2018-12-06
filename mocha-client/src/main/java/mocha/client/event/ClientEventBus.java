package mocha.client.event;

import org.springframework.stereotype.Component;

import mocha.client.input.GameKey;
import mocha.client.input.event.GameKeyEvent;
import mocha.net.event.NetworkedMochaEventBus;

@Component
public class ClientEventBus extends NetworkedMochaEventBus {
  
  public void keyDown(GameKey gameKey) {
    post(new GameKeyEvent(gameKey, true));
  }

  public void keyUp(GameKey gameKey) {
    post(new GameKeyEvent(gameKey, false));
  }

}
