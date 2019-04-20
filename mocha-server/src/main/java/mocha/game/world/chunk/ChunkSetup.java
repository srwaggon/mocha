package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameSetup;
import mocha.game.world.Location;

@Component
class ChunkSetup implements GameSetup {

  private ChunkService chunkService;

  @Inject
  ChunkSetup(ChunkService chunkService) {
    this.chunkService = chunkService;
  }

  @Override
  public void run() {
    int radius = 0;
    for (int y = -radius; y <= radius; y++) {
      for (int x = -radius; x <= radius; x++) {
        chunkService.getOrCreateChunkByIndex(new Location(x, y));
      }
    }
  }
}
