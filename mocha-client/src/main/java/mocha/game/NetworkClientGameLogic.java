package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.event.PlayerAddedEvent;
import mocha.game.event.PlayerRemovedEvent;
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
import mocha.net.packet.PacketSender;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.Repository;
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
  private TaskService taskService;

  @Inject
  private PacketHandler packetHandler;

  @Inject
  private Repository<Entity, Integer> entityRepository;

  private MochaConnection mochaConnection;

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    mochaConnection = connectedEvent.getMochaConnection();
    PacketSender packetSender = packetSenderFactory.newPacketSender(mochaConnection);
    PacketListener packetListener = new PacketListener(eventBus, mochaConnection, -1, packetHandler);
    eventBus.register(packetSender);
    eventBus.register(packetListener);
    taskService.submit(packetListener);
  }

  private void requestChunkData(int x, int y) {
    Location location = new Location(x, y);
    mochaConnection.requestChunk(location);
    mochaConnection.requestEntitiesInChunk(location);
  }

  @Subscribe
  public void handle(PlayerAddedEvent playerAddedEvent) {
    int playerId = playerAddedEvent.getPlayer().getId();
    mochaConnection.requestEntitiesByPlayerId(playerId);

    requestChunkData(-1, -1);
    requestChunkData(-1, 0);
    requestChunkData(0, -1);
    requestChunkData(0, 0);
  }

  @Subscribe
  public void handle(PlayerRemovedEvent playerRemovedEvent) {

  }

  @Subscribe
  public void handle(EntityUpdatedEvent entityUpdatedEvent) {
    Entity entityUpdate = entityUpdatedEvent.getEntity();
    createEntityIfAbsent(entityUpdate);
    updateEntity(entityUpdate);
  }

  private void updateEntity(Entity entityUpdate) {
    entityRepository
        .findById(entityUpdate.getId())
        .ifPresent(entity ->
            entity.getLocation()
                .set(entityUpdate.getLocation()));
  }

  private void createEntityIfAbsent(Entity entityUpdate) {
    if (entityRepository.findById(entityUpdate.getId()).isPresent()) {
      return;
    }
    Entity entity = entityFactory.newSlider();
    entity.setId(entityUpdate.getId());
    entity.getLocation().set(entityUpdate.getLocation());
    game.addEntity(entity);
  }

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    eventBus.postSendPacketEvent(packetFactory.newMovePacket(entityMoveCommand));
  }

  @Subscribe
  public void entityMoveCommandSubscription(EntityMoveCommand entityMoveCommand) {
    entityRepository
        .findById(entityMoveCommand.getEntityId())
        .ifPresent(entity -> {
          entity.getLocation().set(entityMoveCommand.getLocation());
          Movement movement = entity.getMovement();
          movement.handle(entityMoveCommand);
          eventBus.postMoveEvent(movement);
        });
  }

}
