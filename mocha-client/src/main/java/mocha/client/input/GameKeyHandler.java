package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.MochaClientEventBus;
import mocha.client.input.event.KeyDownEvent;
import mocha.game.Registry;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

@Component
public class GameKeyHandler {

  @Inject
  private Registry<Entity> entityRegistry;

  @Inject
  private MochaClientEventBus mochaClientEventBus;

  @PostConstruct
  public void init() {
    mochaClientEventBus.register(this);
  }

  @Subscribe
  public void handle(KeyDownEvent keyDownEvent) {
    int entityId = 0;
    entityRegistry.get(entityId).ifPresent(entity -> {
          Optional<EntityMoveCommand> optionalEntityMove = getEntityMove(keyDownEvent, entityId);
          optionalEntityMove.ifPresent(mochaClientEventBus::sendMovePacket);
        }
    );
  }

  private Optional<EntityMoveCommand> getEntityMove(KeyDownEvent keyDownEvent, int entityId) {
    return getDirection(keyDownEvent).map(direction -> EntityMoveCommand.builder()
        .entityId(entityId)
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