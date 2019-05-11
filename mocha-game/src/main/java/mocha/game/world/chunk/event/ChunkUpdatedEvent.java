package mocha.game.world.chunk.event;

import mocha.game.event.MochaEvent;
import mocha.game.world.chunk.Chunk;

public class ChunkUpdatedEvent implements MochaEvent {
  private final Chunk chunk;

  public ChunkUpdatedEvent(Chunk chunk) {
    this.chunk = chunk;
  }

  public Chunk getChunk() {
    return chunk;
  }
}
