package mocha.game.world.tile;

import javafx.scene.paint.Color;

import static mocha.gfx.Colors.DIRT_BROWN;
import static mocha.gfx.Colors.GRASS_GREEN;
import static mocha.gfx.Colors.STONE_GREY;


enum TileType {

  DIRT(".", DIRT_BROWN),
  GRASS("w", GRASS_GREEN),
  STONE("o", STONE_GREY);

  private String symbol;
  private Color color;

  TileType(String symbol, Color color) {
    this.color = color;
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public Color getColor() {
    return color;
  }
}
