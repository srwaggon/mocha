package mocha.game.world.collision;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;

@Configuration
public class CollisionConfiguration {

  @Bean
  public CollisionFactory collisionFactory(ChunkService chunkService, EntitiesInChunkService entitiesInChunkService) {
    return new CollisionFactory(chunkService, entitiesInChunkService);
  }

}
