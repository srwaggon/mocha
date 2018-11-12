package mocha.game.world.tile;


import java.util.Arrays;

public enum TileType {

  GRID('x', false),
  DIRT('.', false),
  STONE('o', true),
  GRASS('w', false),
  WATER('~', false),
  ;

  public static final int SIZE = 32;

  private char symbol;
  private boolean isBlocking;

  TileType(char symbol, boolean isBlocking) {
    this.symbol = symbol;
    this.isBlocking = isBlocking;
  }

  public char getSymbol() {
    return symbol;
  }

  public boolean isBlocking() {
    return isBlocking;
  }

  public static int count() {
    return values().length;
  }

  public static TileType valueOf(char c) {
    return Arrays.stream(TileType.values())
        .filter(tileType -> tileType.getSymbol() == c)
        .findFirst()
        .orElse(GRID);
  }

  public static TileType random() {
    int chooseRandomExcludingGrid = (int) ((TileType.count() - 1) * Math.random()) + 1;
    return values()[chooseRandomExcludingGrid];
  }
}
