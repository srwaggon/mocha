package mocha.game.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileType;

public class Chunk {

  public final static int SIZE = 16;

  private TileType[] tiles;
  private Set<Entity> entities;

  public Chunk() {
    tiles = new TileType[Chunk.SIZE * Chunk.SIZE];
    entities = Sets.newHashSet();
  }

  public Chunk(TileType[] tiles, Set<Entity> entities) {
    this.tiles = tiles;
    this.entities = entities;
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

  public String buildTileData() {
    return Arrays.stream(tiles)
        .map(TileType::getSymbol)
        .collect(Collectors.joining());
  }

  public void add(Entity entity) {
    entities.add(entity);
  }

  public void remove(Entity entity) {
    entities.remove(entity);
  }

  public List<Entity> getEntities() {
    return Lists.newArrayList(entities);
  }

  public List<Entity> getEntitiesAt(Location location) {
    int x = getXBoundToChunk(location);
    int y = getYBoundToChunk(location);
    return getEntitiesAt(x, y);
  }

  private List<Entity> getEntitiesAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Lists.newLinkedList(getEntities(xIndex, yIndex))
        : Collections.emptyList();
  }

  private Set<Entity> getEntities(int x, int y) {
    int tileIndex = getTileIndex(x, y);
    return entities;
  }

  private int getTileIndex(int x, int y) {
    return x + y * SIZE;
  }

  public static ChunkBuilder builder() {
    return new ChunkBuilder();
  }

  static class ChunkBuilder {

    private TileType[] tiles;
    private Set<Entity> entities;

    private ChunkBuilder() {
    }

    public ChunkBuilder tiles(TileType[] tiles) {
      this.tiles = tiles;
      return this;
    }

    public ChunkBuilder entities(Set<Entity> entities) {
      this.entities = entities;
      return this;
    }

    public Chunk build() {
      return new Chunk(tiles, entities);
    }
  }
}
