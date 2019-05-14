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
  private Map<Integer, Integer> entityIdsByPlayerId = Maps.newConcurrentMap();
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
    getEntityForPlayer(player).ifPresent(entityService::removeEntity);
    playerRepository.delete(player);
    eventBus.postPlayerRemovedEvent(player);
  }

  public Player addPlayer(Player player) {
    Player savedPlayer = playerRepository.save(player);
    eventBus.postPlayerAddedEvent(savedPlayer);
    return savedPlayer;
  }

  public Optional<Entity> getEntityForPlayer(Player player) {
    return getEntityForPlayerId(player.getId());
  }

  public Optional<Entity> getEntityForPlayerId(Integer playerId) {
    return Optional.ofNullable(entityIdsByPlayerId.get(playerId))
        .flatMap(entityId -> entityService.findById(entityId));
  }

  public void addEntityToPlayer(Entity entity, Player player) {
    entityIdsByPlayerId.put(player.getId(), entity.getId());
  }

  public void setClientPlayerId(int clientPlayerId) {
    this.clientPlayerId = clientPlayerId;
  }

  public Optional<Entity> findClientPlayerEntity() {
    return getEntityForPlayerId(clientPlayerId);
  }

  public void deleteAll() {
    playerRepository.findAll()
        .forEach(this::removePlayer);
  }

  public Optional<Player> findById(int playerId) {
    return playerRepository.findById(playerId);
  }
}
