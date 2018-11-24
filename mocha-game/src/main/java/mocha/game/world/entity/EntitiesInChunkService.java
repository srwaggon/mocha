package mocha.game.world.entity;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.chunk.Chunk;
import mocha.shared.Repository;

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
        .collect(Collectors.toSet());
  }

  public void put(Chunk chunk, Entity entity) {
    getChunkSet(chunk.getId()).add(entity.getId());
  }

  public void remove(Chunk chunk, Entity entity) {
    getChunkSet(chunk.getId()).remove(entity.getId());
  }

  private Set<Integer> getChunkSet(int chunkId) {
    Optional<Set<Integer>> chunkSet = Optional.ofNullable(entityIdsByChunk.get(chunkId));
    if (chunkSet.isPresent()) {
      return chunkSet.get();
    }
    entityIdsByChunk.put(chunkId, Sets.newHashSet());
    return getChunkSet(chunkId);
  }
}
