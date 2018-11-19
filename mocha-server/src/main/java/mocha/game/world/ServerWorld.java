package mocha.game.world;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ServerChunk;
import mocha.game.world.chunk.ServerChunkRepository;

@Component
public class ServerWorld extends World {

  @Inject
  private ServerChunkRepository serverChunkRepository;

  @Override
  public void putAtChunkIndex(Location chunkIndex, Chunk chunk) {
    int chunkId = getIdForChunkIndex(chunkIndex);
    ServerChunk serverChunk = new ServerChunk(chunkId, chunk);
    serverChunkRepository.save(serverChunk);
  }

  @Override
  public Optional<Chunk> getChunkAt(Location location) {
    int chunkId = getIdForChunkAt(location);
    return Optional.ofNullable(serverChunkRepository.findOne(chunkId));
  }

  public static int getIdForChunkAt(Location location) {
    return getIdForChunkIndex(location.getChunkIndex());
  }

  public static int getIdForChunkIndex(Location chunkIndices) {
    int radius = Math.max(Math.abs(chunkIndices.getX()), Math.abs(chunkIndices.getY()));
    if (radius == 0) {
      return 1;
    }
    int distance = distanceFromRingOrigin(chunkIndices, radius);
    int previousRingDiameter = 1 + ((radius - 1) * 2);
    return (int) (Math.pow(previousRingDiameter, 2) + distance) + 1;
  }

  private static int distanceFromRingOrigin(Location chunkIndices, int radius) {
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
