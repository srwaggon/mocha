package mocha.game;

import java.util.List;

import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

public class Game implements Tickable {

  private MochaEventBus eventBus;
  private List<GameRule> gameRules;
  private Repository<Entity, Integer> entityRepository;
  private Repository<Player, Integer> playerRepository;
  private Repository<Movement, Integer> movementRepository;
  private ChunkService chunkService;

  public Game(
      MochaEventBus eventBus,
      List<GameRule> gameRules,
      Repository<Player, Integer> playerRepository, Repository<Entity, Integer> entityRepository,
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService
  ) {
    this.eventBus = eventBus;
    this.gameRules = gameRules;
    this.entityRepository = entityRepository;
    this.playerRepository = playerRepository;
    this.movementRepository = movementRepository;
    this.chunkService = chunkService;
  }

  @Override
  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public Entity addEntity(Entity entity) {
    Entity result = entityRepository.save(entity);
    Location entityLocation = result.getLocation();
    chunkService.getChunkAt(entityLocation).add(result);
    eventBus.postEntityAddedEvent(result);
    return result;
  }

  public void removeEntity(int entityId) {
    entityRepository.findById(entityId).ifPresent(this::removeEntity);
  }

  public void removeEntity(Entity entity) {
    entityRepository.delete(entity);
    Location entityLocation = entity.getLocation();
    chunkService.getChunkAt(entityLocation).remove(entity);
    movementRepository.findById(entity.getId()).ifPresent(movementRepository::delete);
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
