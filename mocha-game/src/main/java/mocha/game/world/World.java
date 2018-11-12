package mocha.game.world;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

public class World {

  private Map<Location, Chunk> chunks = Maps.newConcurrentMap();

  public Optional<Chunk> getChunkAt(Location location) {
    return Optional.ofNullable(chunks.get(location.getChunkIndex()));
  }

  public void add(Entity entity) {
    getChunkAt(entity.getLocation()).ifPresent(chunk -> chunk.add(entity));
  }

  public void remove(Entity entity) {
    getChunkAt(entity.getLocation()).ifPresent(chunk -> chunk.remove(entity));
  }

  public Chunk put(Location location, Chunk chunk) {
    chunks.put(location.getChunkIndex(), chunk);
    return chunk;
  }
}
