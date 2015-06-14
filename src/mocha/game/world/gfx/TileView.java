package mocha.game.world.gfx;

import mocha.game.gfx.Renderable;
import mocha.game.world.Tile;

import java.awt.*;

public class TileView implements Renderable {

  private final Tile tile;
  private final int x;
  private final int y;

  public TileView(Tile tile, int x, int y) {
    this.tile = tile;
    this.x = x;
    this.y = y;
  }

  @Override public void paint(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.drawRect(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
  }
}
