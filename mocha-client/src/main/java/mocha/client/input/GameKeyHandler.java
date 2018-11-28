package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.KeyDownEvent;
import mocha.game.Player;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketFactory;
import mocha.shared.Repository;

@Component
public class GameKeyHandler {

  private Repository<Player, Integer> playerRepository;
  private Repository<Entity, Integer> entityRepository;
  private ClientEventBus eventBus;
  private PacketFactory packetFactory;

  @Inject
  public GameKeyHandler(
      Repository<Player, Integer> playerRepository,
      Repository<Entity, Integer> entityRepository,
      ClientEventBus eventBus,
      PacketFactory packetFactory
  ) {
    this.playerRepository = playerRepository;
    this.entityRepository = entityRepository;
    this.eventBus = eventBus;
    this.packetFactory = packetFactory;
  }

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handle(KeyDownEvent keyDownEvent) {
    handleIfMove(keyDownEvent);
    handleIfPickup(keyDownEvent);
  }

  private void handleIfMove(KeyDownEvent keyDownEvent) {
    getDirection(keyDownEvent)
        .ifPresent(direction ->
            findPlayerEntity().ifPresent(entity ->
                handle(buildEntityMoveCommand(entity, direction))));
  }

  private void handle(EntityMoveCommand entityMoveCommand) {
    eventBus.postSendPacketEvent(packetFactory.newMovePacket(entityMoveCommand));
  }

  private void handleIfPickup(KeyDownEvent keyDownEvent) {
    if (!keyDownEvent.getGameKey().equals(GameKey.PICKUP)) {
      return;
    }
    findPlayerEntity().ifPresent(entity ->
        eventBus.postSendPacketEvent(packetFactory.newPickUpItemPacket(entity)));
  }

  private Optional<Entity> findPlayerEntity() {
    return entityRepository.findById(getPlayerEntityId());
  }

  private Integer getPlayerEntityId() {
    return getPlayer().getEntity().getId();
  }

  private Player getPlayer() {
    return playerRepository.findAll().get(0);
  }

  private EntityMoveCommand buildEntityMoveCommand(Entity entity, Direction direction) {
    return EntityMoveCommand.builder()
        .entityId(entity.getId())
        .location(entity.getLocation())
        .direction(direction)
        .build();
  }

  private Optional<Direction> getDirection(KeyDownEvent keyDownEvent) {
    switch (keyDownEvent.getGameKey()) {
      case UP:
        return Optional.of(Direction.NORTH);
      case RIGHT:
        return Optional.of(Direction.EAST);
      case DOWN:
        return Optional.of(Direction.SOUTH);
      case LEFT:
        return Optional.of(Direction.WEST);
      default:
        return Optional.empty();
    }
  }
}
