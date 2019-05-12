package mocha.game.world.entity;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import mocha.game.world.chunk.Chunk;
import mocha.shared.Repository;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toSet;

public class EntitiesInChunkService {

  private Map<Integer, Set<Integer>> entityIdsByChunk = Maps.newConcurrentMap();

  private Repository<Entity, Integer> entityRepository;

  public EntitiesInChunkService(Repository<Entity, Integer> entityRepository) {
    this.entityRepository = entityRepository;
  }

  public Set<Entity> getEntitiesInChunk(Chunk chunk) {
    return getChunkSet(chunk.getId()).stream()
        .map(entityRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toSet());
  }

  public void put(Chunk chunk, Entity entity) {
    getChunkSet(chunk.getId()).add(entity.getId());
  }

  public void remove(Chunk chunk, Entity entity) {
    Optional.ofNullable(entityIdsByChunk.get(chunk.getId()))
        .orElse(newHashSet())
        .remove(entity.getId());
  }

  private Set<Integer> getChunkSet(int chunkId) {
    Optional<Set<Integer>> chunkSet = Optional.ofNullable(entityIdsByChunk.get(chunkId));
    if (!chunkSet.isPresent()) {
      Set<Integer> newSet = newHashSet();
      entityIdsByChunk.put(chunkId, newSet);
      return newSet;
    }
    return chunkSet.get();
  }
}
