package mocha.game;

import java.util.List;

import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class Game implements Tickable {

  private MochaEventBus eventBus;
  private World world;
  private List<GameRule> gameRules;
  private Repository<Entity, Integer> entityRepository;
  private Repository<Player, Integer> playerRepository;

  public Game(MochaEventBus eventBus, World world, List<GameRule> gameRules, Repository<Entity, Integer> entityRepository, Repository<Player, Integer> playerRepository) {
    this.eventBus = eventBus;
    this.world = world;
    this.gameRules = gameRules;
    this.entityRepository = entityRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public World getWorld() {
    return world;
  }

  public void addEntity(Entity entity) {
    entityRepository.save(entity);
    world.add(entity);
    eventBus.postEntityAddedEvent(entity);
  }

  public void removeEntity(int entityId) {
    entityRepository.findById(entityId).ifPresent(this::removeEntity);
  }

  public void removeEntity(Entity entity) {
    entityRepository.delete(entity);
    world.remove(entity);
    eventBus.postEntityRemovedEvent(entity);
  }

  public void addPlayer(Player player) {
    playerRepository.save(player);
    addEntity(player.getEntity());
  }

  public void removePlayer(int playerId) {
    playerRepository.findById(playerId).ifPresent(this::removePlayer);
  }

  private void removePlayer(Player player) {
    player.remove();
    removeEntity(player.getEntity());
    playerRepository.delete(player);
  }

  public Repository<Entity, Integer> getEntityRepository() {
    return entityRepository;
  }

  public Repository<Player, Integer> getPlayerRepository() {
    return playerRepository;
  }
}
