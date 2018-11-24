package mocha.game.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileType;
import mocha.shared.Identified;

public class Chunk implements Identified<Integer> {

  public final static int SIZE = 16;

  private int id;
  private TileType[] tiles = new TileType[Chunk.SIZE * Chunk.SIZE];
  private Set<Entity> entities = Sets.newHashSet();

  public Chunk() {
  }

  public Chunk(int id, TileType[] tiles) {
    this.id = id;
    this.tiles = tiles;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
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

  public void setTile(int x, int y, TileType tileType) {
    tiles[getTileIndex(x, y)] = tileType;
  }

  public void setTiles(TileType[] tiles) {
    this.tiles = tiles;
  }

  private Optional<TileType> getTileAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return getTileWithIndices(xIndex, yIndex);
  }

  private Optional<TileType> getTileWithIndices(int xIndex, int yIndex) {
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

  public List<TileType> getTileNeighbors(int x, int y) {
    ArrayList<TileType> results = Lists.newArrayList();
    getTileWithIndices(x, y - 1).ifPresent(results::add);
    getTileWithIndices(x + 1, y).ifPresent(results::add);
    getTileWithIndices(x, y + 1).ifPresent(results::add);
    getTileWithIndices(x - 1, y).ifPresent(results::add);
    return results;
  }

  public void remove(Entity entity) {
    entities.remove(entity);
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
