package mocha.game.world.tile;

import javafx.scene.paint.Color;

import static mocha.gfx.Colors.DIRT_BROWN;
import static mocha.gfx.Colors.GRASS_GREEN;
import static mocha.gfx.Colors.STONE_GREY;
import static mocha.gfx.Colors.VOID_PINK;


public enum TileType {

  GRID(" ", VOID_PINK, 0),
  DIRT(".", DIRT_BROWN, 1),
  STONE("o", STONE_GREY, 4),
  GRASS("w", GRASS_GREEN, 160),
  WATER("~", STONE_GREY, 192),
  ;

  private String symbol;
  private Color color;
  private int spriteIndex;

  TileType(String symbol, Color color, int spriteIndex) {
    this.color = color;
    this.symbol = symbol;
    this.spriteIndex = spriteIndex;
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
}
