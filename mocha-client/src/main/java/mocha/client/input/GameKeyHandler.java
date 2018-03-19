package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.KeyDownEvent;
import mocha.game.Registry;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketFactory;

@Component
public class GameKeyHandler {

  @Inject
  private Registry<Entity> entityRegistry;

  @Inject
  private ClientEventBus eventBus;

  @Inject
  private PacketFactory packetFactory;

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handle(KeyDownEvent keyDownEvent) {
    int entityId = 0;
    entityRegistry.get(entityId)
        .ifPresent(entity ->
            getEntityMove(keyDownEvent, entity)
                .ifPresent(this::sendMovePacket)
        );
  }

  private void sendMovePacket(EntityMoveCommand entityMove) {
    eventBus.postSendPacketEvent(packetFactory.movePacket(entityMove));
  }

  private Optional<EntityMoveCommand> getEntityMove(KeyDownEvent keyDownEvent, Entity entity) {
    return getDirection(keyDownEvent).map(direction -> EntityMoveCommand.builder()
        .entityId(entity.getId())
        .location(entity.getLocation())
        .direction(direction)
        .build());
  }

  private Optional<Direction> getDirection(KeyDownEvent keyDownEvent) {
    if (keyDownEvent.getGameKey().equals(GameKey.UP)) {
      return Optional.of(Direction.NORTH);
    } else if (keyDownEvent.getGameKey().equals(GameKey.RIGHT)) {
      return Optional.of(Direction.EAST);
    } else if (keyDownEvent.getGameKey().equals(GameKey.DOWN)) {
      return Optional.of(Direction.SOUTH);
    } else if (keyDownEvent.getGameKey().equals(GameKey.LEFT)) {
      return Optional.of(Direction.WEST);
    }
    return Optional.empty();
  }
}
