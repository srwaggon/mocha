package mocha.game;

import java.util.List;

import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.ChunkRepository;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class Game implements Tickable {

  private MochaEventBus eventBus;
  private ChunkRepository chunkRepository;
  private List<GameRule> gameRules;
  private Repository<Entity, Integer> entityRepository;
  private Repository<Player, Integer> playerRepository;

  public Game(MochaEventBus eventBus, ChunkRepository chunkRepository, List<GameRule> gameRules, Repository<Entity, Integer> entityRepository, Repository<Player, Integer> playerRepository) {
    this.eventBus = eventBus;
    this.chunkRepository = chunkRepository;
    this.gameRules = gameRules;
    this.entityRepository = entityRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public ChunkRepository getChunkRepository() {
    return chunkRepository;
  }

  public Entity addEntity(Entity entity) {
    Entity result = entityRepository.save(entity);
    chunkRepository.add(result);
    eventBus.postEntityAddedEvent(result);
    return result;
  }

  public void removeEntity(int entityId) {
    entityRepository.findById(entityId).ifPresent(this::removeEntity);
  }

  public void removeEntity(Entity entity) {
    entityRepository.delete(entity);
    chunkRepository.remove(entity);
    eventBus.postEntityRemovedEvent(entity);
  }

  public void addPlayer(Player player) {
    playerRepository.save(player);
    eventBus.postPlayerAddedEvent(player);
  }

  void removePlayer(int playerId) {
    playerRepository.findById(playerId)
        .ifPresent(this::removePlayer);
  }

  private void removePlayer(Player player) {
    player.remove();
    removeEntity(player.getEntity());
    playerRepository.delete(player);
    eventBus.postPlayerRemovedEvent(player);
  }

}
