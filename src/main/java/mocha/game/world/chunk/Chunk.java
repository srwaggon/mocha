package mocha.game.world.chunk;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Builder;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileType;

@Builder
public class Chunk {

  public final static int SIZE = 16;

  private TileType[] tiles;
  private List<List<Entity>> entities;

  public Chunk() {
    tiles = new TileType[Chunk.SIZE * Chunk.SIZE];
    entities = Lists.newArrayList();

    for (int i = 0; i < Chunk.SIZE * Chunk.SIZE; i++) {
      entities.add(Lists.newArrayList());
    }
  }

  public Chunk(TileType[] tiles, List<List<Entity>> entities) {
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
    return tiles[x + y * SIZE];
  }

  private Optional<TileType> getTileAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Optional.of(getTile(xIndex, yIndex))
        : Optional.empty();
  }

  public Optional<TileType> getTileAt(Location location) {
    Location locationInChunk = boundToChunk(location);
    return getTileAt(locationInChunk.getXAsInt(), locationInChunk.getYAsInt());
  }

  private Location boundToChunk(Location location) {
    int x = getXBoundToChunk(location);
    int y = getYBoundToChunk(location);
    return new Location(x, y);
  }

  private int getXBoundToChunk(Location location) {
    return getBoundToChunk(location.getXAsInt(), Chunk.getWidth());
  }

  private int getYBoundToChunk(Location location) {
    return getBoundToChunk(location.getYAsInt(), Chunk.getHeight());
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
    Location entityLocation = boundToChunk(entity.getMovement().getLocation());
    int x = entityLocation.getXAsInt() / TileType.SIZE;
    int y = entityLocation.getYAsInt() / TileType.SIZE;
    int tileIndex = x + y * SIZE;
    entities.get(tileIndex).add(entity);
  }

  public void remove(Entity entity) {
    Location entityLocation = boundToChunk(entity.getMovement().getLocation());
    int x = entityLocation.getXAsInt() / TileType.SIZE;
    int y = entityLocation.getYAsInt() / TileType.SIZE;
    int tileIndex = x + y * SIZE;
    entities.get(tileIndex).remove(entity);
  }

  public List<Entity> getEntities() {
    List<Entity> allEntities = Lists.newArrayList();
    entities.forEach(allEntities::addAll);
    return allEntities;
  }

  public List<Entity> getEntitiesAt(Location location) {
    Location locationInChunk = boundToChunk(location);
    return getEntitiesAt(locationInChunk.getXAsInt(), locationInChunk.getYAsInt());
  }

  private List<Entity> getEntitiesAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Lists.newLinkedList(getEntities(xIndex, yIndex))
        : Collections.emptyList();
  }

  private List<Entity> getEntities(int x, int y) {
    int tileIndex = x + y * SIZE;
    return entities.get(tileIndex);
  }
}
