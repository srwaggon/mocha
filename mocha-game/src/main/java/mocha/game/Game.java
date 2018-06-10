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
  private Registry<Player> playerRegistry;

  public Game(MochaEventBus eventBus, World world, List<GameRule> gameRules, Registry<Entity> entityRegistry, Registry<Player> playerRegistry) {
    this.eventBus = eventBus;
    this.world = world;
    this.gameRules = gameRules;
    this.entityRegistry = entityRegistry;
    this.playerRegistry = playerRegistry;
  }

  @Override
  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public World getWorld() {
    return world;
  }

  public void addEntity(Entity entity) {
    entityRegistry.add(entity);
    world.add(entity);
    eventBus.postEntityAddedEvent(entity);
  }

  public void removeEntity(int entityId) {
    entityRegistry.get(entityId).ifPresent(this::removeEntity);
  }

  public void removeEntity(Entity entity) {
    entityRegistry.remove(entity);
    world.remove(entity);
    eventBus.postEntityRemovedEvent(entity);
  }

  public void addPlayer(Player player) {
    playerRegistry.add(player);
    addEntity(player.getEntity());
  }

  public void removePlayer(int playerId) {
    playerRegistry.get(playerId).ifPresent(this::removePlayer);
  }

  private void removePlayer(Player player) {
    player.remove();
    removeEntity(player.getEntity());
    playerRegistry.remove(player);
  }

  public Registry<Entity> getEntityRegistry() {
    return entityRegistry;
  }

  public Registry<Player> getPlayerRegistry() {
    return playerRegistry;
  }
}
