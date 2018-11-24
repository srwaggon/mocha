package mocha.game.world.tile.event;

import mocha.game.world.chunk.Chunk;

public class TileUpdatedEvent {

  private final Chunk chunk;
  private final int x;
  private final int y;

  public TileUpdatedEvent(Chunk chunk, int x, int y) {
    this.chunk = chunk;
    this.x = x;
    this.y = y;
  }

  public Chunk getChunk() {
    return chunk;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
