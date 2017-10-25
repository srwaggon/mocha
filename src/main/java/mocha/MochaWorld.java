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
    this.setChunks(createchunks());
  }

  private Map<Location, Chunk> createchunks() {
    Map<Location, Chunk> chunks = Maps.newHashMap();
    for (int y = -1; y < 1; y++) {
      for (int x = -1; x < 1; x++) {
        Location location = new Location(x, y);
        Chunk chunk = chunkFactory.newRandomDefault();
        chunks.put(location, chunk);
      }
    }
    return chunks;
  }
}
