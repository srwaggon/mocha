package mocha.game.world;

public class Map {

  private Tile[][] tiles;

  public Map(int rows, int columns) {
    tiles = new Tile[rows][columns];
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < columns; y++) {
        tiles[x][y] = new Tile();
      }
    }
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public Tile getTile(int x, int y) {
    return tiles[x][y];
  }
}
