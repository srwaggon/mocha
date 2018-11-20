package mocha.game.world;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

public class World {

  private Map<Integer, Chunk> chunks = Maps.newConcurrentMap();

  public Optional<? extends Chunk> getChunkAt(Location location) {
    int chunkId = getIdForChunkAt(location);
    return Optional.ofNullable(chunks.get(chunkId));
  }

  public void add(Entity entity) {
    getChunkAt(entity.getLocation())
        .ifPresent(chunk -> chunk.add(entity));
  }

  public void remove(Entity entity) {
    getChunkAt(entity.getLocation())
        .ifPresent(chunk -> chunk.remove(entity));
  }

  public void put(Location location, Chunk chunk) {
    int chunkId = getIdForChunkAt(location);
    chunks.put(chunkId, chunk);
  }

  static int getIdForChunkAt(Location location) {
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
