package mocha.client.event;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.input.GameKey;
import mocha.client.input.event.KeyDownEvent;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.PacketFactory;

@Component
public class MochaClientEventBus extends NetworkedMochaEventBus {

  @Inject
  public MochaClientEventBus(PacketFactory packetFactory) {
    super(packetFactory);
  }

  public void keyDown(GameKey gameKey) {
    post(new KeyDownEvent(gameKey));
  }
}
