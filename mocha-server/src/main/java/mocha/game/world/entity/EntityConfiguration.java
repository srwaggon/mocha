package mocha.game.world.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.event.MochaEventBus;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.item.ItemService;
import mocha.shared.CachingRepository;
import mocha.shared.IdFactory;
import mocha.shared.Repository;

@Configuration
public class EntityConfiguration {

  @Bean
  public EntityFactory entityFactory(ItemService itemService) {
    return new EntityFactory(itemService);
  }

  @Bean
  public IdFactory<Entity> entityIdFactory(Repository<Entity, Integer> entityRepository) {
    return new IdFactory<>(entityRepository);
  }


  @Bean
  public Repository<Entity, Integer> entityRepository(ServerEntityToEntityAdapterRepository entityRepository) {
    return new CachingRepository<>(entityRepository);
  }

  @Bean
  public EntitiesInChunkService entitiesInChunkService(Repository<Entity, Integer> entityRepository) {
    return new EntitiesInChunkService(entityRepository);
  }


  @Bean
  public EntityService entityService(
      MochaEventBus eventBus, Repository<Entity, Integer> entityRepository,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      Repository<Movement, Integer> movementRepository
  ) {
    return new EntityService(eventBus, entityRepository, entitiesInChunkService, chunkService, movementRepository);
  }

}
