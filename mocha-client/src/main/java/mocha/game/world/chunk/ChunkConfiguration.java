package mocha.game.world.chunk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class ChunkConfiguration {

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @Inject
  private MochaEventBus mochaEventBus;

  @Bean
  public ChunkService chunkService(
      ChunkFactory chunkFactory,
      Repository<Chunk, Integer> chunkRepository
  ) {
    return isOnline
        ? new NetworkChunkService(mochaEventBus, chunkFactory, chunkRepository)
        : new ChunkService(mochaEventBus, chunkFactory, chunkRepository);
  }

  @Bean
  public ChunkFactory chunkFactory(TileSetFactory tileSetFactory) {
    return new ChunkFactory(tileSetFactory);
  }

  @Bean
  public Repository<Chunk, Integer> chunkRepository() {
    return new InMemoryRepository<>();
  }
}
