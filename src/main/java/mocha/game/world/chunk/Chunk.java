package mocha.game.world.chunk;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mocha.game.world.Location;
import mocha.game.world.tile.TileType;

@Data
@Builder
@AllArgsConstructor
public class Chunk {

  public final static int SIZE = 16;

  private TileType[][] tiles;

  public TileType getTile(int x, int y) {
    return tiles[y][x];
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

  public Optional<TileType> getTileAt(int x, int y) {
    int xIndex = x / TileType.SIZE;
    int yIndex = y / TileType.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Optional.of(getTile(xIndex, yIndex))
        : Optional.empty();
  }

  public Optional<TileType> getTileAt(Location location) {
    int x = (Chunk.getWidth() + location.getXAsInt() % Chunk.getWidth()) % Chunk.getWidth();
    int y = (Chunk.getHeight() + location.getYAsInt() % Chunk.getHeight()) % Chunk.getHeight();
    Location locationInChunk = new Location(x, y);
    return getTileAt(locationInChunk.getXAsInt(), locationInChunk.getYAsInt());
  }
}
