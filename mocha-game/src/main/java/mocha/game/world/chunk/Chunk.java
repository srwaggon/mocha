package mocha.game.world.chunk;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileType;
import mocha.shared.Identified;

public class Chunk implements Identified<Integer> {

  public final static int SIZE = 16;

  int id;
  TileType[] tiles;
  Set<Entity> entities;

  public Chunk() {
    tiles = new TileType[Chunk.SIZE * Chunk.SIZE];
    entities = Sets.newHashSet();
  }

  public Chunk(TileType[] tiles, Set<Entity> entities) {
    this.tiles = tiles;
    this.entities = entities;
  }

  public Chunk(int id, TileType[] tiles, HashSet<Entity> entities) {
    this.id = id;
    this.tiles = tiles;
    this.entities = entities;
  }

  @Override
  public Integer getId() {
    return id;
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

  private boolean inBounds(int value) {
    return 0 <= value && value < SIZE;
  }

  public static int getWidth() {
    return SIZE * TileType.SIZE;
  }

  public static int getHeight() {
    return SIZE * TileType.SIZE;
  }

  public TileType getTile(int x, int y) {
    return tiles[getTileIndex(x, y)];
  }

  public void setTiles(TileType[] tiles) {
    this.tiles = tiles;
  }

  private Optional<TileType> getTileAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Optional.of(getTile(xIndex, yIndex))
        : Optional.empty();
  }

  public Optional<TileType> getTileAt(Location location) {
    int x = getXBoundToChunk(location);
    int y = getYBoundToChunk(location);
    return getTileAt(x, y);
  }

  private int getXBoundToChunk(Location location) {
    return getBoundToChunk(location.getX(), Chunk.getWidth());
  }

  private int getYBoundToChunk(Location location) {
    return getBoundToChunk(location.getY(), Chunk.getHeight());
  }

  private int getBoundToChunk(int xAsInt, int width) {
    return (width + xAsInt % width) % width;
  }

  public TileType[] getTiles() {
    return tiles;
  }

  public void add(Entity entity) {
    entities.add(entity);
  }

  public void remove(Entity entity) {
    entities.remove(entity);
  }

  public Set<Entity> getEntities() {
    return Sets.newHashSet(entities);
  }

  public Set<Entity> getEntitiesAt(Location location) {
    int x = getXBoundToChunk(location);
    int y = getYBoundToChunk(location);
    return getEntitiesAt(x, y);
  }

  private Set<Entity> getEntitiesAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? getEntities(x, y)
        : Collections.emptySet();
  }

  private Set<Entity> getEntities(int x, int y) {
    int tileIndex = getTileIndex(x, y);
    return entities;
  }

  private int getTileIndex(int x, int y) {
    return x + y * SIZE;
  }

}
