package mocha.client.input;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.input.event.KeyDownEvent;
import mocha.game.GameLogic;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketFactory;
import mocha.shared.Repository;

@Component
public class GameKeyHandler {

  @Inject
  private Repository<Entity, Integer> entityRepository;

  @Inject
  private ClientEventBus eventBus;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  private GameLogic gameLogic;

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Subscribe
  public void handle(KeyDownEvent keyDownEvent) {
    handleIfMove(keyDownEvent);
  }

  private void handleIfMove(KeyDownEvent keyDownEvent) {
    getDirection(keyDownEvent)
        .ifPresent(direction -> entityRepository.findById(0)
            .ifPresent(entity ->
                gameLogic.handle(buildEntityMoveCommand(entity, direction))));
  }

  private EntityMoveCommand buildEntityMoveCommand(Entity entity, Direction direction) {
    return EntityMoveCommand.builder()
        .entityId(entity.getId())
        .location(entity.getLocation())
        .direction(direction)
        .build();
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
