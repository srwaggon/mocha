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
    entityRegistry.get(0).ifPresent(entity -> {
          Optional<EntityMoveCommand> optionalEntityMove = getEntityMove(keyDownEvent, entity);
          optionalEntityMove.ifPresent(mochaClientEventBus::sendMoveRequest);
        }
    );
  }

  private Optional<EntityMoveCommand> getEntityMove(KeyDownEvent keyDownEvent, Entity entity) {
    return getDirection(keyDownEvent).map(direction -> new EntityMoveCommand(entity, direction));
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
