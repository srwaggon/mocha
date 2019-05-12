package mocha.game.world.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.event.MochaEventBus;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.entity.prototype.EntityPrototypeFactory;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.item.ItemService;
import mocha.shared.IdFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class EntityConfiguration {

  @Bean
  public EntityPrototypeFactory entityPrototypeFactory() {
    return new EntityPrototypeFactory();
  }

  @Bean
  public Repository<EntityPrototype, Integer> entityPrototypeRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public IdFactory<EntityPrototype> entityPrototypeIdFactory(Repository<EntityPrototype, Integer> entityPrototypeRepository) {
    return new IdFactory<>(entityPrototypeRepository);
  }

  @Bean
  public EntityPrototypeService entityPrototypeService(EntityPrototypeFactory entityPrototypeFactory, Repository<EntityPrototype, Integer> entityPrototypeRepository, IdFactory<EntityPrototype> entityPrototypeIdFactory) {
    return new EntityPrototypeService(entityPrototypeFactory, entityPrototypeRepository, entityPrototypeIdFactory);
  }

  @Bean
  public EntityFactory entityFactory(ItemService itemService) {
    return new EntityFactory(itemService);
  }

  @Bean
  public IdFactory<Entity> entityIdFactory(Repository<Entity, Integer> entityRepository) {
    return new IdFactory<>(entityRepository);
  }

  @Bean
  public EntitiesInChunkService entitiesInChunkService(Repository<Entity, Integer> entityRepository) {
    return new EntitiesInChunkService(entityRepository);
  }

  @Bean
  public EntityService entityService(
      MochaEventBus eventBus,
      Repository<Entity, Integer> entityRepository,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      Repository<Movement, Integer> movementRepository
  ) {
    return new EntityService(
        eventBus,
        entityRepository,
        entitiesInChunkService,
        chunkService,
        movementRepository
    );
  }

}
