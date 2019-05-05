package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.event.MochaEventHandler;
import mocha.game.player.event.PlayerAddedEvent;
import mocha.game.player.event.PlayerRemovedEvent;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.event.EntityUpdatedEvent;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketResolver;
import mocha.net.packet.PacketSender;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.Repository;
import mocha.shared.task.TaskService;

@Component
public class NetworkClientGameLogic extends ClientGameLogic implements GameLogic {

  private PacketSenderFactory packetSenderFactory;
  private TaskService taskService;
  private PacketResolver packetHandler;
  private Repository<Entity, Integer> entityRepository;
  private MovementFactory movementFactory;
  private MochaConnection mochaConnection;
  private EntityService entityService;

  @Inject
  public NetworkClientGameLogic(
      ClientEventBus eventBus,
      PacketSenderFactory packetSenderFactory,
      TaskService taskService,
      @Lazy PacketResolver packetHandler,
      Repository<Entity, Integer> entityRepository,
      MovementFactory movementFactory,
      Repository<Movement, Integer> movementRepository,
      EntityService entityService,
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      List<MochaEventHandler> eventHandlers,
      List<CommandHandler> commandHandlers
  ) {
    super(eventBus, movementRepository, itemPrototypeService, itemService, eventHandlers, commandHandlers);
    this.packetSenderFactory = packetSenderFactory;
    this.taskService = taskService;
    this.packetHandler = packetHandler;
    this.entityRepository = entityRepository;
    this.movementFactory = movementFactory;
    this.entityService = entityService;
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    mochaConnection = connectedEvent.getMochaConnection();
    PacketSender packetSender = packetSenderFactory.newPacketSender(mochaConnection);
    PacketListener packetListener = new PacketListener(eventBus, mochaConnection, -1, packetHandler);
    eventBus.register(packetSender);
    eventBus.register(packetListener);
    taskService.submit(packetListener);
    sendLoginRequest();

    requestChunkData(-1, -1);
    requestChunkData(-1, 0);
    requestChunkData(0, -1);
    requestChunkData(0, 0);
  }

  private void sendLoginRequest() {
    mochaConnection.sendLoginRequest("link");
  }

  private void requestChunkData(int x, int y) {
    Location location = new Location(x, y);
    mochaConnection.requestChunkAt(location);
    mochaConnection.requestEntitiesInChunk(location);
  }

  @Subscribe
  public void handle(PlayerAddedEvent playerAddedEvent) {
    int playerId = playerAddedEvent.getPlayer().getId();
    mochaConnection.requestEntitiesByPlayerId(playerId);
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
    entityRepository.findById(entityUpdate.getId())
        .ifPresent(entity ->
            entity.getLocation().set(entityUpdate.getLocation()));
  }

  private void createEntityIfAbsent(Entity entity) {
    if (!entityRepository.findById(entity.getId()).isPresent()) {
      Entity resultEntity = entityService.save(entity);
      movementRepository.save(movementFactory.newSlidingMovement(resultEntity));
    }
  }

  @Subscribe
  public void handle(EntityMoveCommand entityMoveCommand) {
    entityRepository
        .findById(entityMoveCommand.getEntityId())
        .ifPresent(entity -> {
          entity.getLocation().set(entityMoveCommand.getLocation());

          movementRepository.findById(entity.getId())
              .ifPresent(movement -> {
                movement.handle(entityMoveCommand);
                eventBus.postMoveEvent(movement);
              });
        });
  }

  public void movePlayerEntity(EntityMoveCommand entityMoveCommand) {
    eventBus.postSendPacketEvent(new MovePacket(entityMoveCommand));
  }

}
