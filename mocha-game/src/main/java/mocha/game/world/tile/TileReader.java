package mocha.game.world.tile;

import mocha.game.world.chunk.Chunk;

public class TileReader {
  public TileType[] readTiles(String tileString) {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    TileType[] tiles = new TileType[rows * columns];
    for (int i = 0; i < tileString.length(); i++) {
      int x = i % columns;
      int y = i / columns;
      char tileTypeSymbol = tileString.charAt(i);
      tiles[y * rows + x] = TileType.valueOf(tileTypeSymbol);
    }
    return tiles;
  }
}
