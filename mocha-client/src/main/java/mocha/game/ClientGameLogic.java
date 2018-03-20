package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.event.EntityUpdateEvent;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSender;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.task.TaskService;

@Component
public class ClientGameLogic {

  @Inject
  private Game game;

  @Inject
  private EntityFactory entityFactory;

  @Inject
  private ClientEventBus eventBus;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private PacketSenderFactory packetSenderFactory;

  @Inject
  private PacketListenerFactory packetListenerFactory;

  @Inject
  private TaskService taskService;

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    MochaConnection connection = connectedEvent.getMochaConnection();
    PacketSender packetSender = packetSenderFactory.newPacketSender(connection);
    eventBus.register(packetSender);
    PacketListener packetListener = packetListenerFactory.newPacketListener(connection, -1);
    eventBus.register(packetListener);
    taskService.submit(packetListener);

    requestMapData(new Location(-1, -1));
    requestMapData(new Location(-1, 0));
    requestMapData(new Location(0, -1));
    requestMapData(new Location(0, 0));
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
    eventBus.postSendPacketEvent(packetFactory.newChunkRequestPacket(location));
    eventBus.postSendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(location));
  }

}
