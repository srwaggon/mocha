package mocha.game.world;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class World {

  private Map<Location, Chunk> chunks = Maps.newHashMap();

  public Optional<Chunk> getChunkAt(Location location) {
    return Optional.ofNullable(chunks.get(location.getChunkIndex()));
  }

  // TODO: Deprecate, forcing usage of Chunk.getWidth()
  public Optional<Chunk> getChunk(Location location) {
    return Optional.ofNullable(chunks.get(location));
  }

  public void add(Entity entity) {
    getChunkAt(entity.getMovement().getLocation()).ifPresent(chunk -> chunk.add(entity));
  }
}
