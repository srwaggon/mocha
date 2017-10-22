package mocha.game.world;

import org.springframework.stereotype.Component;

import java.util.HashMap;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class World {

  @Inject
  private ChunkFactory chunkFactory;

  private HashMap<Integer, Chunk> world = new HashMap<>();

  public void addMap(Chunk chunk) {
    world.put(chunk.getId(), chunk);
  }

  public Chunk getMapById(int mapId) {
    return world.get(mapId);
  }
}
