package mocha.game.player;

import java.util.Optional;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.shared.Repository;


public class PlayerService {

  private MochaEventBus eventBus;
  private Repository<Player, Integer> playerRepository;
  private EntityService entityService;

  public PlayerService(
      MochaEventBus eventBus,
      Repository<Player, Integer> playerRepository,
      EntityService entityService
  ) {
    this.eventBus = eventBus;
    this.playerRepository = playerRepository;
    this.entityService = entityService;
  }

  public Entity getEntityForPlayer() {
    return getPlayer().getEntity();
  }

  public Player getPlayer() {
    return playerRepository.findAll().get(0);
  }

  public void removePlayer(int playerId) {
    playerRepository.findById(playerId)
        .ifPresent(this::removePlayer);
  }

  private void removePlayer(Player player) {
    player.remove();
    entityService.removeEntity(player.getEntity());
    playerRepository.delete(player);
    eventBus.postPlayerRemovedEvent(player);
  }

  public void addPlayer(Player player) {
    playerRepository.save(player);
    eventBus.postPlayerAddedEvent(player);
  }

  public Integer getPlayerEntityId() {
    return getEntityForPlayer().getId();
  }

  public Optional<Entity> findPlayerEntity() {
    Integer playerEntityId = getPlayerEntityId();
    return entityService.findById(playerEntityId);
  }

  public void deleteAll() {
    playerRepository.findAll()
        .forEach(this::removePlayer);
  }

  public Optional<Player> findById(int playerId) {
    return playerRepository.findById(playerId);
  }
}
