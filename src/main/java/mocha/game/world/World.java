package mocha.game.world;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  public Collection<Chunk> getChunks() {
    return chunks.values();
  }
}
