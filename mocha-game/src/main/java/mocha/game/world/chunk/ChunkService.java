package mocha.game.world.chunk;

import mocha.game.world.Location;
import mocha.shared.Repository;

public class ChunkService {

  private ChunkFactory chunkFactory;
  private Repository<Chunk, Integer> chunkRepository;


  public ChunkService(
      ChunkFactory chunkFactory,
      Repository<Chunk, Integer> chunkRepository
  ) {
    this.chunkFactory = chunkFactory;
    this.chunkRepository = chunkRepository;
  }

  public Chunk getChunkAt(Location location) {
    int chunkId = getIdForChunkAt(location);
    return getChunkById(chunkId);
  }

  private int getIdForChunkAt(Location location) {
    return getIdForChunkIndex(location.getChunkIndex());
  }

  public Chunk getChunkByIndex(Location chunkIndex) {
    int chunkId = getIdForChunkIndex(chunkIndex);
    return getChunkById(chunkId);
  }

  public Chunk getChunkById(int chunkId) {
    return chunkRepository.findById(chunkId).orElseGet(() ->
        chunkRepository.save(chunkFactory.newRandomChunk(chunkId)));
  }

  private int getIdForChunkIndex(Location chunkIndices) {
    int radius = Math.max(Math.abs(chunkIndices.getX()), Math.abs(chunkIndices.getY()));
    if (radius == 0) {
      return 1;
    }
    int distance = distanceFromRingOrigin(chunkIndices, radius);
    int previousRingDiameter = 1 + ((radius - 1) * 2);
    return (int) (Math.pow(previousRingDiameter, 2) + distance) + 1;
  }


  private int distanceFromRingOrigin(Location chunkIndices, int radius) {
    int x = chunkIndices.getX();
    int y = chunkIndices.getY();
    int stepCount = 0;

    if (x == -radius && y == -radius) {
      return 0;
    }

    if (x == -radius && y < radius) {
      stepCount += radius - y;
      y = radius;
    }

    if (y == radius && x < radius) {
      stepCount += radius - x;
      x = radius;
    }

    if (x == radius && y > -radius) {
      stepCount += y + radius;
      y = -radius;
    }

    if (y == -radius && x > -radius) {
      stepCount += x + radius;
    }

    return stepCount;
  }
}
