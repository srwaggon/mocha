package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.Location;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemService;
import mocha.net.packet.PacketHandler;

@Component
public class EntityUpdatePacketHandler implements PacketHandler {

  private MochaEventBus clientEventBus;
  private ItemService itemService;

  @Inject
  public EntityUpdatePacketHandler(MochaEventBus clientEventBus, ItemService itemService) {
    this.clientEventBus = clientEventBus;
    this.itemService = itemService;
  }

  @Subscribe
  public void handle(EntityUpdatePacket entityUpdatePacket) {
    clientEventBus.postEntityUpdatedEvent(createEntity(entityUpdatePacket));
  }

  private Entity createEntity(EntityUpdatePacket entityUpdatePacket) {
    int entityId = entityUpdatePacket.getEntityId();
    Location location = entityUpdatePacket.getLocation();
    EntityType entityType = entityUpdatePacket.getEntityType();
    int typeId = entityUpdatePacket.getTypeId();

    switch (entityType) {
      case ITEM:
        Item item = itemService.findById(typeId);
        return new ItemEntity(entityId, location, item);
      case MOB:
      default:
        return new Entity(entityId, location);
    }
  }
}