package mocha.game.world.chunk;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mocha.game.world.Location;
import mocha.game.world.tile.Tile;

@Data
@Builder
@AllArgsConstructor
public class Chunk {

  private Tile[][] tiles;
  public final static int SIZE = 16;

  public Tile getTile(int x, int y) {
    return tiles[y][x];
  }

  private boolean inBounds(int value) {
    return 0 <= value && value < SIZE;
  }

  public static int getWidth() {
    return SIZE * Tile.SIZE;
  }

  public static int getHeight() {
    return SIZE * Tile.SIZE;
  }

  public Optional<Tile> getTileAt(int x, int y) {
    int xIndex = x / Tile.SIZE;
    int yIndex = y / Tile.SIZE;
    return inBounds(xIndex) && inBounds(yIndex)
        ? Optional.of(getTile(xIndex, yIndex))
        : Optional.empty();
  }

  public Optional<Tile> getTileAt(Location location) {
    return getTileAt(location.getXAsInt(), location.getYAsInt());
  }
}
