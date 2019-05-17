package mocha.game.world.collision;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntityService;

@Configuration
public class CollisionConfiguration {

  @Bean
  public CollisionFactory collisionFactory(ChunkService chunkService, EntityService entityService) {
    return new CollisionFactory(chunkService, entityService);
  }

}
