package mocha.game.world.chunk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.event.MochaEventBus;
import mocha.game.world.chunk.tile.TileSetFactory;
import mocha.shared.Repository;

@Configuration
public class ChunkConfiguration {

  @Bean
  public ChunkService chunkService(
      MochaEventBus mochaEventBus,
      ChunkFactory chunkFactory,
      Repository<Chunk, Integer> chunkRepository
  ) {
    return new ChunkService(mochaEventBus, chunkFactory, chunkRepository);
  }

  @Bean
  public ChunkFactory chunkFactory(TileSetFactory tileSetFactory) {
    return new ChunkFactory(tileSetFactory);
  }

}
