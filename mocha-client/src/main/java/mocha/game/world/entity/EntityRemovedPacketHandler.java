package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.net.packet.PacketHandler;

@Component
public class EntityRemovedPacketHandler implements PacketHandler {

  private EntityService entityService;

  @Inject
  public EntityRemovedPacketHandler(EntityService entityService) {
    this.entityService = entityService;
  }

  @Subscribe
  public void handle(EntityRemovedPacket entityRemovedPacket) {
    entityService.removeEntity(entityRemovedPacket.getEntityId());
  }
}