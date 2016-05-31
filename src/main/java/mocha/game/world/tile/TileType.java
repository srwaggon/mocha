package mocha.game.world.tile;

import javafx.scene.paint.Color;

import static mocha.game.gfx.Colors.DIRT_BROWN;


public enum TileType {

  DIRT(".", DIRT_BROWN),
  ;

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
