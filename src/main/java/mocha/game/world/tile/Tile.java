package mocha.game.world.tile;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;

public class Tile implements Renderable {

  public static final int SIZE = 32;
  private final int x, y;
  private final TileType tileType;

  public Tile(int x, int y, TileType tileType) {
    this.x = x;
    this.y = y;
    this.tileType = tileType;
  }

  public Tile(int x, int y) {
    this(x, y, TileType.DIRT);
  }

  public TileType getTileType() {
    return tileType;
  }

  public String getSymbol() {
    return getTileType().getSymbol();
  }

  @Override
  public void render(GraphicsContext graphics) {
    graphics.setFill(getTileType().getColor());
    graphics.fillRect(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
  }
}
