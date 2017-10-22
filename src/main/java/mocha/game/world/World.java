package mocha.game.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.chunk.Chunk;

@Builder
@AllArgsConstructor
public class World {

  private Chunk[][] chunks;

  public Chunk getChunkAt(Location location) {
    return chunks[getChunkY(location)][getChunkX(location)];
  }

  private int getChunkX(Location location) {
    return location.getX() >= 0
        ? (int) (location.getX() / Chunk.getWidth())
        : (int) (location.getX() / Chunk.getWidth()) - 1;
  }

  private int getChunkY(Location location) {
    return location.getY() >= 0
        ? (int) (location.getY() / Chunk.getHeight())
        : (int) (location.getY() / Chunk.getHeight()) - 1;
  }
}
