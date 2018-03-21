package mocha.game;

import java.util.List;

import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.shared.Registry;

public class Game implements Tickable {

  private MochaEventBus eventBus;
  private World world;
  private List<GameRule> gameRules;
  private Registry<Entity> entityRegistry;

  public Game(MochaEventBus eventBus, World world, List<GameRule> gameRules, Registry<Entity> entityRegistry) {
    this.eventBus = eventBus;
    this.world = world;
    this.gameRules = gameRules;
    this.entityRegistry = entityRegistry;
  }

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public World getWorld() {
    return world;
  }

  public void add(Entity entity) {
    entityRegistry.add(entity);
    world.add(entity);
    eventBus.postAddEntityEvent(entity);
  }

  public void remove(Entity entity) {
    entityRegistry.remove(entity);
    world.remove(entity);
    eventBus.postRemoveEntityEvent(entity);
  }

  public Registry<Entity> getEntityRegistry() {
    return entityRegistry;
  }
}
