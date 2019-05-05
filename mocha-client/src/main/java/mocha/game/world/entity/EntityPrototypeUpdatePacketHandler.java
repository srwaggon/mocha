package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.world.entity.prototype.EntityPrototypeUpdatePacket;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.net.packet.PacketHandler;

@Component
public class EntityPrototypeUpdatePacketHandler implements PacketHandler<EntityPrototypeUpdatePacket> {

  private ClientEventBus clientEventBus;

  @Inject
  public EntityPrototypeUpdatePacketHandler(ClientEventBus clientEventBus) {
    this.clientEventBus = clientEventBus;
  }

  @Subscribe
  public void handle(EntityPrototypeUpdatePacket entityPrototypeUpdatePacket) {
    clientEventBus.post(new UpdateEntityPrototypeCommand(entityPrototypeUpdatePacket.getEntityPrototype()));
  }
}