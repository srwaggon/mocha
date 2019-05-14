package mocha.game.player;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;

import static com.google.common.collect.Lists.newArrayList;

@org.springframework.stereotype.Repository
public class ServerPlayerToPlayerAdapterRepository implements Repository<Player, Integer> {

  @Inject
  private ServerPlayerJpaRepository serverPlayerJpaRepository;

  @Override
  public List<Player> findAll() {
    return newArrayList(serverPlayerJpaRepository.findAll());
  }

  @Override
  public Player save(Player player) {
    ServerPlayer serverPlayer = new ServerPlayer(player.getId());
    return serverPlayerJpaRepository.save(serverPlayer);
  }

  @Override
  public Optional<Player> findById(Integer playerId) {
    Optional<ServerPlayer> byId = serverPlayerJpaRepository.findById(playerId);
    //noinspection OptionalIsPresent
    return byId.isPresent() ? Optional.of(byId.get()) : Optional.empty();
  }

  @Override
  public void delete(Player player) {
    serverPlayerJpaRepository.findById(player.getId())
        .ifPresent(serverPlayerJpaRepository::delete);
  }

  @Override
  public void deleteAll() {
    serverPlayerJpaRepository.deleteAll();
  }
}
