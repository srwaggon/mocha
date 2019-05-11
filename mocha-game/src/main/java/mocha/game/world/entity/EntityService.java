package mocha.game.world.entity;

import java.util.List;
import java.util.Optional;

import mocha.game.event.MochaEventBus;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

public class EntityService {

  private MochaEventBus eventBus;
  private Repository<Entity, Integer> entityRepository;
  private EntitiesInChunkService entitiesInChunkService;
  private ChunkService chunkService;
  private Repository<Movement, Integer> movementRepository;

  public EntityService(
      MochaEventBus eventBus, Repository<Entity, Integer> entityRepository,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      Repository<Movement, Integer> movementRepository
  ) {
    this.eventBus = eventBus;
    this.entityRepository = entityRepository;
    this.entitiesInChunkService = entitiesInChunkService;
    this.chunkService = chunkService;
    this.movementRepository = movementRepository;
  }

  public Entity save(Entity entity) {
    Entity result = entityRepository.save(entity);
    addEntityToChunk(result);
    eventBus.postEntityAddedEvent(result);
    return result;
  }

  private void addEntityToChunk(Entity entity) {
    Location entityLocation = entity.getLocation();
    Chunk chunk = chunkService.getOrCreateChunkAt(entityLocation);
    entitiesInChunkService.put(chunk, entity);
  }

  public void removeEntity(int entityId) {
    entityRepository.findById(entityId).ifPresent(this::removeEntity);
  }

  public void removeEntity(Entity entity) {
    if (entity.isTransient()) {
      entityRepository.delete(entity);
    }
    removeEntityFromChunk(entity);
    movementRepository.findById(entity.getId()).ifPresent(movementRepository::delete);
    eventBus.postEntityRemovedEvent(entity);
  }

  private void removeEntityFromChunk(Entity entity) {
    Location entityLocation = entity.getLocation();
    Chunk chunk = chunkService.getOrCreateChunkAt(entityLocation);
    entitiesInChunkService.remove(chunk, entity);
  }

  public Optional<Entity> findById(Integer playerEntityId) {
    return entityRepository.findById(playerEntityId);
  }

  public List<Entity> findAll() {
    return entityRepository.findAll();
  }
}
