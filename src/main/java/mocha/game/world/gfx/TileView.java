package mocha.game.world.gfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mocha.game.gfx.Renderable;
import mocha.game.world.tile.Tile;


public class TileView implements Renderable {

  private final Tile tile;
  private final int x;
  private final int y;

  public TileView(Tile tile, int x, int y) {
    this.tile = tile;
    this.x = x;
    this.y = y;
  }

  @Override public void paint(GraphicsContext graphics) {
    graphics.setFill(Color.BLACK);
    graphics.fillRect(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);

    graphics.setStroke(Color.GREEN);
    graphics.strokeRect(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
  }
}
