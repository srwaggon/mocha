package mocha.client.event;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.input.GameKey;
import mocha.client.input.event.KeyDownEvent;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.world.entity.movement.action.MovePacket;

@Component
public class MochaClientEventBus extends NetworkedMochaEventBus {

  @Inject
  public MochaClientEventBus(PacketFactory packetFactory) {
    super(packetFactory);
  }

  public void keyDown(GameKey gameKey) {
    post(new KeyDownEvent(gameKey));
  }

  public void sendMoveRequest(EntityMoveCommand entityMove) {
    sendPacket(new MovePacket(entityMove));
  }
}
