package mocha.game.world.tile;

public enum TileType {

  DIRT("."),
  ;

  private String symbol;

  TileType(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }
}
