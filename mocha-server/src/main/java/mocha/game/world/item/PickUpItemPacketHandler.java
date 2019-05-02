package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import mocha.game.world.entity.EntityService;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

public class PickUpItemPacketHandler implements PacketHandler {

  private EntityService entityService;
  private ServerEventBus serverEventBus;

  public PickUpItemPacketHandler(EntityService entityService, ServerEventBus serverEventBus) {
    this.entityService = entityService;
    this.serverEventBus = serverEventBus;
  }

  @Subscribe
  private void handle(PickUpItemPacket pickUpItemPacket) {
    entityService.findById(pickUpItemPacket.getEntityId()).ifPresent(entity -> serverEventBus.postPickUpItemCommand(entity));
  }
}