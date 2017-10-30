package mocha.game.world.chunk;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileType;

@Data
@Builder
@AllArgsConstructor
public class Chunk {

  public final static int SIZE = 16;

  private TileType[] tiles;
  private List<Entity>[] tileEntities;

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

  public Optional<TileType> getTileAt(int x, int y) {
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
    int x = (Chunk.getWidth() + location.getXAsInt() % Chunk.getWidth()) % Chunk.getWidth();
    int y = (Chunk.getHeight() + location.getYAsInt() % Chunk.getHeight()) % Chunk.getHeight();
    return new Location(x, y);
  }


  public void add(Entity entity) {
    Location entityLocation = boundToChunk(entity.getMovement().getLocation());
    int x = entityLocation.getXAsInt() / TileType.SIZE;
    int y = entityLocation.getYAsInt() / TileType.SIZE;
    tileEntities[x + y * SIZE].add(entity);
  }

  public void remove(Entity entity) {
    Location entityLocation = boundToChunk(entity.getMovement().getLocation());
    int x = entityLocation.getXAsInt() / TileType.SIZE;
    int y = entityLocation.getYAsInt() / TileType.SIZE;
    tileEntities[x + y * SIZE].remove(entity);
  }

  public List<Entity> getEntities() {
    List<Entity> allEntities = Lists.newArrayList();
    Arrays.stream(getTileEntities()).forEach(allEntities::addAll);
    return allEntities;
  }

  public List<Entity> getEntitiesAt(Location location) {
    Location locationInChunk = boundToChunk(location);
    return getEntitiesAt(locationInChunk.getXAsInt(), locationInChunk.getYAsInt());
  }

  public List<Entity> getEntitiesAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Lists.newLinkedList(getEntities(xIndex, yIndex))
        : Collections.emptyList();
  }

  public List<Entity> getEntities(int x, int y) {
    return tileEntities[x + y * SIZE];
  }
}
