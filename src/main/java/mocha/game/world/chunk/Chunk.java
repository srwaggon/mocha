package mocha.game.world.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

  public static int getWidth() {
    return SIZE * Tile.SIZE;
  }

  public static int getHeight() {
    return SIZE * Tile.SIZE;
  }

  public Tile getTileAt(int x, int y) {
    return getTile(x / Tile.SIZE, y / Tile.SIZE);
  }
}
