package mocha.client;

import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Optional;

import mocha.client.input.InputKey;
import mocha.client.input.KeyDownEvent;
import mocha.game.Game;
import mocha.game.event.world.entity.AddEntityEvent;
import mocha.game.event.world.entity.EntityUpdateEvent;
import mocha.game.rule.GameRule;
import mocha.game.world.Direction;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.EntityRegistry;
import mocha.game.world.entity.movement.EntityMove;

public class MochaClientGame extends Game {
  private final EntityFactory entityFactory;
  private final MochaClientEventBus eventBus;

  MochaClientGame(World world, List<GameRule> gameRules, EntityFactory entityFactory, EntityRegistry entityRegistry, MochaClientEventBus eventBus) {
    super(world, gameRules, entityRegistry);
    this.entityFactory = entityFactory;
    this.eventBus = eventBus;
  }

  @Subscribe
  public void handle(AddEntityEvent addEntityEvent) {
    this.add(addEntityEvent.getEntity());
  }

  @Subscribe
  public void handle(EntityUpdateEvent entityUpdateEvent) {
    Entity entityUpdate = entityUpdateEvent.getEntity();
    createEntityIfAbsent(entityUpdate);
    updateEntity(entityUpdate);
  }

  private void updateEntity(Entity entityUpdate) {
    entityRegistry.get(entityUpdate.getId())
        .ifPresent(entity ->
            entity.getLocation()
                .set(entityUpdate.getLocation()));
  }

  private void createEntityIfAbsent(Entity entityUpdate) {
    if (!entityRegistry.getIds().contains(entityUpdate.getId())) {
      Entity entity = entityFactory.createSlider();
      entity.setId(entityUpdate.getId());
      entity.getLocation().set(entityUpdate.getLocation());
      eventBus.addEntity(entity);
    }
  }

  @Subscribe
  public void handle(KeyDownEvent keyDownEvent) {
    entityRegistry.get(0).ifPresent(entity -> {
          Optional<EntityMove> optionalEntityMove = getEntityMove(keyDownEvent, entity);
          optionalEntityMove.ifPresent(entityMove -> entity.getMovement().handle(entityMove));
          optionalEntityMove.ifPresent(eventBus::sendMoveRequest);
        }
    );
  }

  private Optional<EntityMove> getEntityMove(KeyDownEvent keyDownEvent, Entity entity) {
    return getDirection(keyDownEvent).map(direction -> new EntityMove(entity, direction));
  }

  private Optional<Direction> getDirection(KeyDownEvent keyDownEvent) {
    if (keyDownEvent.getInputKey().equals(InputKey.UP)) {
      return Optional.of(Direction.NORTH);
    } else if (keyDownEvent.getInputKey().equals(InputKey.RIGHT)) {
      return Optional.of(Direction.EAST);
    } else if (keyDownEvent.getInputKey().equals(InputKey.DOWN)) {
      return Optional.of(Direction.SOUTH);
    } else if (keyDownEvent.getInputKey().equals(InputKey.LEFT)) {
      return Optional.of(Direction.WEST);
    }
    return Optional.empty();
  }

}
