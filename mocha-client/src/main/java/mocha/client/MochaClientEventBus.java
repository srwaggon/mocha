package mocha.client;

import org.springframework.stereotype.Component;

import mocha.client.input.InputKey;
import mocha.client.input.KeyDownEvent;
import mocha.game.world.entity.Entity;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.world.entity.movement.action.MoveRequestPacket;

@Component
public class MochaClientEventBus extends NetworkedMochaEventBus {

  public void keyDown(InputKey inputKey) {
    post(new KeyDownEvent(inputKey));
  }

  public void sendMoveRequest(Entity entity) {
    sendPacket(new MoveRequestPacket(entity));
  }
}
