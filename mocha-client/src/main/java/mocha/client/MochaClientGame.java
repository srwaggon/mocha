package mocha.client;

import com.google.common.eventbus.Subscribe;

import java.util.List;

import mocha.game.Game;
import mocha.game.event.world.entity.EntityUpdateEvent;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.event.world.entity.AddEntityEvent;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.EntityRegistry;

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
            entity.getMovement()
                .getLocation()
                .set(entityUpdate.getMovement().getLocation()));
  }

  private void createEntityIfAbsent(Entity entityUpdate) {
    if (!entityRegistry.getIds().contains(entityUpdate.getId())) {
      Entity entity = entityFactory.createSimple();
      entity.setId(entityUpdate.getId());
      eventBus.addEntity(entity);
    }
  }

}
