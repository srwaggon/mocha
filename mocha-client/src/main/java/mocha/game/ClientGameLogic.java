package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.MochaClientEventBus;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.event.AddEntityEvent;
import mocha.game.world.entity.event.EntityUpdateEvent;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.event.SendPacketEvent;

@Component
public class ClientGameLogic {
  @Inject
  private Game game;
  @Inject
  private EntityFactory entityFactory;
  @Inject
  private MochaClientEventBus eventBus;
  @Inject
  private PacketFactory packetFactory;

  @PostConstruct
  public void init() {
    eventBus.register(this);
    requestMapData(new Location(-1, -1));
    requestMapData(new Location(-1, 0));
    requestMapData(new Location(0, -1));
    requestMapData(new Location(0, 0));
  }

  @Subscribe
  public void handle(AddEntityEvent addEntityEvent) {

  }

  @Subscribe
  public void handle(EntityUpdateEvent entityUpdateEvent) {
    Entity entityUpdate = entityUpdateEvent.getEntity();
    createEntityIfAbsent(entityUpdate);
    updateEntity(entityUpdate);
  }

  private void updateEntity(Entity entityUpdate) {
    game.getEntityRegistry().get(entityUpdate.getId())
        .ifPresent(entity ->
            entity.getLocation()
                .set(entityUpdate.getLocation()));
  }

  private void createEntityIfAbsent(Entity entityUpdate) {
    if (!game.getEntityRegistry().getIds().contains(entityUpdate.getId())) {
      Entity entity = entityFactory.createSlider();
      entity.setId(entityUpdate.getId());
      entity.getLocation().set(entityUpdate.getLocation());
      game.add(entity);
    }
  }

  private void requestMapData(Location location) {
    eventBus.post(new SendPacketEvent(packetFactory.newChunkRequestPacket(location)));
    eventBus.post(new SendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(location)));
  }

}
