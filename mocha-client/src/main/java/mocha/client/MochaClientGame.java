package mocha.client;

import com.google.common.eventbus.Subscribe;

import java.util.List;

import mocha.client.event.MochaClientEventBus;
import mocha.game.Game;
import mocha.game.Registry;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.event.AddEntityEvent;
import mocha.game.world.entity.event.EntityUpdateEvent;

public class MochaClientGame extends Game {
  private final EntityFactory entityFactory;
  private final MochaClientEventBus eventBus;

  MochaClientGame(World world, List<GameRule> gameRules, EntityFactory entityFactory, Registry<Entity> entityRegistry, MochaClientEventBus eventBus) {
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

}
