package mocha.game.world.tile;

import javafx.scene.paint.Color;

import static mocha.gfx.Colors.DIRT_BROWN;
import static mocha.gfx.Colors.GRASS_GREEN;
import static mocha.gfx.Colors.STONE_GREY;
import static mocha.gfx.Colors.VOID_PINK;
import static mocha.gfx.Colors.WATER_BLUE;


public enum TileType {

  GRID("x", VOID_PINK, 0, false),
  DIRT(".", DIRT_BROWN, 1, false),
  STONE("o", STONE_GREY, 224, true),
  GRASS("w", GRASS_GREEN, 160, false),
  WATER("~", WATER_BLUE, 192, false),
  ;

  public static final int SIZE = 32;

  private String symbol;
  private Color color;
  private int spriteIndex;
  private boolean isBlocking;

  TileType(String symbol, Color color, int spriteIndex, boolean isBlocking) {
    this.color = color;
    this.symbol = symbol;
    this.spriteIndex = spriteIndex;
    this.isBlocking = isBlocking;
  }

  public String getSymbol() {
    return symbol;
  }

  public Color getColor() {
    return color;
  }

  public int getSprite() {
    return this.spriteIndex;
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
