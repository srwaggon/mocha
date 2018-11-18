package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.event.EntityUpdatedEvent;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketHandler;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSender;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.task.TaskService;

@Component
public class NetworkClientGameLogic implements GameLogic {

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

  @Inject
  private PacketHandler packetHandler;

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    MochaConnection connection = connectedEvent.getMochaConnection();
    PacketSender packetSender = packetSenderFactory.newPacketSender(connection);
    PacketListener packetListener = packetListenerFactory.newPacketListener(connection, -1, packetHandler);
    eventBus.register(packetSender);
    eventBus.register(packetListener);
    taskService.submit(packetListener);

    requestMapData(new Location(-1, -1));
    requestMapData(new Location(-1, 0));
    requestMapData(new Location(0, -1));
    requestMapData(new Location(0, 0));
  }

  @Subscribe
  public void handle(EntityUpdatedEvent entityUpdatedEvent) {
    Entity entityUpdate = entityUpdatedEvent.getEntity();
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
      Entity entity = entityFactory.newSlider();
      entity.setId(entityUpdate.getId());
      entity.getLocation().set(entityUpdate.getLocation());
      game.addEntity(entity);
    }
  }

  private void requestMapData(Location location) {
    eventBus.postSendPacketEvent(packetFactory.newChunkRequestPacket(location));
    eventBus.postSendPacketEvent(packetFactory.newRequestEntitiesInChunkPacket(location));
  }

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    eventBus.postSendPacketEvent(packetFactory.newMovePacket(entityMoveCommand));
  }

  @Subscribe
  public void entityMoveCommandSubscription(EntityMoveCommand entityMoveCommand) {
    game.getEntityRegistry()
        .get(entityMoveCommand.getEntityId())
        .ifPresent(entity -> {
          entity.getLocation().set(entityMoveCommand.getLocation());
          Movement movement = entity.getMovement();
          movement.handle(entityMoveCommand);
          eventBus.postMoveEvent(movement);
        });
  }

}
