package mocha.client.event;

import org.springframework.stereotype.Component;

import mocha.client.input.GameKey;
import mocha.client.input.event.KeyDownEvent;
import mocha.net.event.NetworkedMochaEventBus;

@Component
public class ClientEventBus extends NetworkedMochaEventBus {
  
  public void keyDown(GameKey gameKey) {
    post(new KeyDownEvent(gameKey));
  }

}
