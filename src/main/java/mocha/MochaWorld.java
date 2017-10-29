package mocha;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;

@Component
public class MochaWorld extends World {

  @Inject
  private ChunkFactory chunkFactory;

  @PostConstruct
  public void init() {
    this.setChunks(createChunks());
  }

  private Map<Location, Chunk> createChunks() {
    Map<Location, Chunk> chunks = Maps.newHashMap();
    for (int y = -16; y < 16; y++) {
      for (int x = -16; x < 16; x++) {
        Location location = new Location(x, y);
        Chunk chunk = chunkFactory.newRandomDefault(x, y);
        chunks.put(location, chunk);
      }
    }
    return chunks;
  }
}
