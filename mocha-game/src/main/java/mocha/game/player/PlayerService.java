package mocha.game.player;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.shared.Repository;


public class PlayerService {

  private MochaEventBus eventBus;
  private Repository<Player, Integer> playerRepository;
  private EntityService entityService;
  private Map<Integer, Entity> entitiesByPlayerId = Maps.newConcurrentMap();
  private int clientPlayerId;

  public PlayerService(
      MochaEventBus eventBus,
      Repository<Player, Integer> playerRepository,
      EntityService entityService
  ) {
    this.eventBus = eventBus;
    this.playerRepository = playerRepository;
    this.entityService = entityService;
  }

  public void removePlayer(int playerId) {
    playerRepository.findById(playerId)
        .ifPresent(this::removePlayer);
  }

  private void removePlayer(Player player) {
    Optional.ofNullable(entitiesByPlayerId.get(player.getId()))
        .ifPresent(entityService::removeEntity);
    playerRepository.delete(player);
    eventBus.postPlayerRemovedEvent(player);
  }

  public void addPlayer(Player player) {
    playerRepository.save(player);
    eventBus.postPlayerAddedEvent(player);
  }

  public Entity getEntityForPlayer(Player player) {
    return entitiesByPlayerId.get(player.getId());
  }

  public void addEntityToPlayer(Entity entity, Player player) {
    entitiesByPlayerId.put(player.getId(), entity);
  }

  public void setClientPlayerId(int clientPlayerId) {
    this.clientPlayerId = clientPlayerId;
  }

  public Optional<Entity> findClientPlayerEntity() {
    return Optional.ofNullable(entitiesByPlayerId.get(clientPlayerId))
        .flatMap(entity -> entityService.findById(entity.getId()));
  }

  public void deleteAll() {
    playerRepository.findAll()
        .forEach(this::removePlayer);
  }

  public Optional<Player> findById(int playerId) {
    return playerRepository.findById(playerId);
  }
}
