package mocha.game.world.tile;


public enum TileType {

  GRID("x", false),
  DIRT(".", false),
  STONE("o", true),
  GRASS("w", false),
  WATER("~", false),
  ;

  public static final int SIZE = 32;

  private String symbol;
  private boolean isBlocking;

  TileType(String symbol, boolean isBlocking) {
    this.symbol = symbol;
    this.isBlocking = isBlocking;
  }

  public String getSymbol() {
    return symbol;
  }

  public boolean isBlocking() {
    return isBlocking;
  }

  public static int count() {
    return values().length;
  }

  public static TileType random() {
    int chooseRandomExcludingGrid = (int) ((TileType.count() - 1) * Math.random()) + 1;
    return values()[chooseRandomExcludingGrid];
  }
}
