package mocha.game.world.tile;


import java.util.Arrays;

import mocha.game.world.collision.Collider;

public enum TileType implements Collider {

  GRID('+', false),
  DIRT('.', false),
  STONE('#', true),
  GRASS('_', false),
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

  @Override
  public boolean isBlocking() {
    return isBlocking;
  }

  @Override
  public void collide(Collider collider) {

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
