package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.net.packet.PacketHandler;

@Component
public class MovePacketHandler implements PacketHandler<MovePacket> {

  private ClientEventBus clientEventBus;

  @Inject
  public MovePacketHandler(ClientEventBus clientEventBus) {
    this.clientEventBus = clientEventBus;
  }

  @Subscribe
  public void handle(MovePacket movePacket) {
    clientEventBus.post(movePacket.getMoveCommand());
  }
}