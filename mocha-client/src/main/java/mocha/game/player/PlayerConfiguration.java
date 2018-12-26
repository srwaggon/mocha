package mocha.game.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.EntityService;
import mocha.shared.IdFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class PlayerConfiguration {

  @Bean
  public IdFactory<Player> playerIdFactory(Repository<Player, Integer> playerRepository) {
    return new IdFactory<>(playerRepository);
  }

  @Bean
  public Repository<Player, Integer> playerRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public PlayerService playerService(
      MochaEventBus eventBus,
      Repository<Player, Integer> playerRepository,
      EntityService entityService
  ) {
    return new PlayerService(eventBus, playerRepository, entityService);
  }

}
