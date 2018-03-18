package mocha.game;

import java.util.List;

import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;

public class Game implements Tickable {

  private List<GameRule> gameRules;
  private World world;
  protected Registry<Entity> entityRegistry;

  public Game(World world, List<GameRule> gameRules, Registry<Entity> entityRegistry) {
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
  }

  public Registry<Entity> getEntityRegistry() {
    return entityRegistry;
  }
}
