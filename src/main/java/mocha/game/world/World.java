package mocha.game.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.chunk.Chunk;

@Builder
@AllArgsConstructor
public class World {

  private Chunk[][] chunks;

  public Chunk getChunkAt(Location location) {
    int intX = (int) (location.getX() / Chunk.getWidth());
    int intY = (int) (location.getY() / Chunk.getHeight());
    return chunks[intY][intX];
  }
}
