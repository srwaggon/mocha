package mocha.game.world;

import com.google.common.collect.Maps;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.chunk.Chunk;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class World {

  private Map<Location, Chunk> chunks = Maps.newHashMap();

  public Chunk getChunkAt(Location location) {
    return chunks.get(location.getChunkIndex());
  }

  public Chunk getChunk(Location location) {
    return chunks.get(location);
  }
}
