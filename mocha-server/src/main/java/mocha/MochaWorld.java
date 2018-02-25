package mocha;

import org.springframework.stereotype.Component;

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
    createChunks();
  }

  private void createChunks() {
    for (int y = -16; y < 16; y++) {
      for (int x = -16; x < 16; x++) {
        Location chunkIndex = new Location(x * Chunk.getWidth(), y * Chunk.getHeight());
        Chunk chunk = chunkFactory.newRandomDefault();
        this.put(chunkIndex, chunk);
      }
    }
  }
}
