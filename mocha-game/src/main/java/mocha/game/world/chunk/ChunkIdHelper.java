package mocha.game.world.chunk;

import mocha.game.world.Location;

class ChunkIdHelper {
  static Location getLocationOfChunkById(Integer chunkId) {
    return new Location(getChunkX(chunkId), getChunkY(chunkId));
  }

  private static int getChunkY(Integer chunkId) {
    return getChunkYIndex(chunkId) * Chunk.getHeight();
  }

  private static int getChunkX(Integer chunkId) {
    return getChunkXIndex(chunkId) * Chunk.getWidth();
  }

  static int getChunkXIndex(int chunkId) {
    int ringRadius = getRingRadius(chunkId);
    int ringDiameter = getRingDiameter(chunkId);
    int ringBeginningId = getRingBeginningId(chunkId);
    int steps = chunkId - ringBeginningId;

    if (steps < ringDiameter - 1) {
      return -ringRadius + steps;
    }

    if (steps < 2 * (ringDiameter - 1)) {
      return ringRadius;
    }

    if (steps < 3 * (ringDiameter - 1)) {
      return ringRadius - (steps - (2 * (ringDiameter - 1)));
    }
    return -ringRadius;
  }

  static int getChunkYIndex(int chunkId) {
    int ringRadius = getRingRadius(chunkId);
    int ringDiameter = getRingDiameter(chunkId);
    int ringBeginningId = getRingBeginningId(chunkId);
    int steps = chunkId - ringBeginningId;

    if (steps < ringDiameter - 1) {
      return -ringRadius;
    }

    if (steps < 2 * (ringDiameter - 1)) {
      return -ringRadius + (steps - (ringDiameter - 1));
    }

    if (steps < 3 * (ringDiameter - 1)) {
      return ringRadius;
    }
    return ringRadius - (steps - (3 * (ringDiameter - 1)));
  }

  private static int getRingRadius(int chunkId) {
    return (getRingDiameter(chunkId) - 1) / 2;
  }

  static int getRingBeginningId(int chunkId) {
    return (int) Math.pow(Math.max(0, getRingDiameter(chunkId) - 2), 2) + 1;
  }

  static int getRingDiameter(Integer chunkId) {
    int diameter = 1;
    while (Math.pow(diameter, 2) < chunkId) {
      diameter += 2;
    }
    return diameter;
  }

  static int getIdForChunkAt(Location location) {
    return getIdForChunkIndex(location.getChunkIndex());
  }

  static int getIdForChunkIndex(Location chunkIndices) {
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
