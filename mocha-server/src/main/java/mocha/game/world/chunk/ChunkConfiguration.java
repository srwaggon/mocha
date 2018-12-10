package mocha.game.world.chunk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.shared.CachingRepository;
import mocha.shared.Repository;

@Configuration
public class ChunkConfiguration {

  @Bean
  public Repository<Chunk, Integer> chunkRepository(Repository<Chunk, Integer> chunkRepository) {
    return new CachingRepository<>(chunkRepository);
  }

  @Bean
  public ChunkService chunkService(ChunkFactory chunkFactory, Repository<Chunk, Integer> chunkRepository) {
    return new ChunkService(chunkFactory, chunkRepository);
  }

  @Bean
  public ChunkFactory chunkFactory(TileSetFactory tileSetFactory) {
    return new ChunkFactory(tileSetFactory);
  }

}
