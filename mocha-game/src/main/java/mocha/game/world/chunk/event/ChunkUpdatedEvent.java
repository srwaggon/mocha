package mocha.game.world.chunk.event;

import mocha.game.world.chunk.Chunk;

public class ChunkUpdatedEvent {
  private final Chunk chunk;

  public ChunkUpdatedEvent(Chunk chunk) {
    this.chunk = chunk;
  }

  public Chunk getChunk() {
    return chunk;
  }
}
