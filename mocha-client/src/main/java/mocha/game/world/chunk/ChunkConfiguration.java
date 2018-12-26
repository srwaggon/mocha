package mocha.game.world.chunk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class ChunkConfiguration {


  @Bean
  public ChunkFactory chunkFactory(TileSetFactory tileSetFactory) {
    return new ChunkFactory(tileSetFactory);
  }

  @Bean
  public Repository<Chunk, Integer> chunkRepository() {
    return new InMemoryRepository<>();
  }
}
