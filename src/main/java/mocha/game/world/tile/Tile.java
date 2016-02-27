package mocha.game.world.tile;

public class Tile {

  public static final int SIZE = 32;
  private TileType tileType;

  public Tile(TileType tileType) {
    this.tileType = tileType;
  }

  public Tile() {
    this.tileType = TileType.DIRT;
  }

  public TileType getTileType() {
    return tileType;
  }

  public String getSymbol() {
    return getTileType().getSymbol();
  }
}
