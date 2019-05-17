package mocha.game.world.entity;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import mocha.game.event.MochaEventBus;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toSet;

public class EntityService {

  private MochaEventBus eventBus;
  private Repository<Entity, Integer> entityRepository;
  private ChunkService chunkService;
  private Repository<Movement, Integer> movementRepository;
  private Map<Integer, Set<Integer>> chunkOccupantIdsByChunkId = Maps.newConcurrentMap();

  public EntityService(
      MochaEventBus eventBus, Repository<Entity, Integer> entityRepository,
      ChunkService chunkService,
      Repository<Movement, Integer> movementRepository
  ) {
    this.eventBus = eventBus;
    this.entityRepository = entityRepository;
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
    put(chunk, entity);
  }

  public void removeEntity(int entityId) {
    findById(entityId).ifPresent(this::removeEntity);
  }

  public void removeEntity(Entity entity) {
    if (entity.isTransient()) {
      entityRepository.delete(entity);
      movementRepository.findById(entity.getId()).ifPresent(movementRepository::delete);
    }
    removeEntityFromChunk(entity);
    eventBus.postEntityRemovedEvent(entity);
  }

  private void removeEntityFromChunk(Entity entity) {
    Location entityLocation = entity.getLocation();
    Chunk chunk = chunkService.getOrCreateChunkAt(entityLocation);
    remove(chunk, entity);
  }

  public Optional<Entity> findById(Integer playerEntityId) {
    return entityRepository.findById(playerEntityId);
  }

  public List<Entity> findAll() {
    return entityRepository.findAll();
  }

  public Set<Entity> getEntitiesInChunk(Chunk chunk) {
    return getChunkOccupantIds(chunk.getId()).stream()
        .map(this::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toSet());
  }

  public void put(Chunk chunk, Entity entity) {
    getChunkOccupantIds(chunk.getId()).add(entity.getId());
  }

  public void remove(Chunk chunk, Entity entity) {
    Optional.ofNullable(chunkOccupantIdsByChunkId.get(chunk.getId()))
        .orElse(newHashSet())
        .remove(entity.getId());
  }

  private Set<Integer> getChunkOccupantIds(int chunkId) {
    Optional<Set<Integer>> chunkOccupantIds = Optional.ofNullable(this.chunkOccupantIdsByChunkId.get(chunkId));
    if (!chunkOccupantIds.isPresent()) {
      Set<Integer> newSet = newHashSet();
      this.chunkOccupantIdsByChunkId.put(chunkId, newSet);
      return newSet;
    }
    return chunkOccupantIds.get();
  }
}
