package mocha.client;

import org.springframework.stereotype.Component;

import mocha.client.input.InputKey;
import mocha.client.input.KeyDownEvent;
import mocha.game.world.entity.movement.EntityMove;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.world.entity.movement.action.MovePacket;

@Component
public class MochaClientEventBus extends NetworkedMochaEventBus {

  public void keyDown(InputKey inputKey) {
    post(new KeyDownEvent(inputKey));
  }

  public void sendMoveRequest(EntityMove entityMove) {
    sendPacket(new MovePacket(entityMove));
  }
}
