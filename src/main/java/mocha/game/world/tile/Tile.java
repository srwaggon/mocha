package mocha.game.world.tile;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;
import mocha.game.world.tile.item.TileItem;

public class Tile implements Renderable {

  public static final int SIZE = 32;
  private final int x, y;
  private TileType tileType = TileType.DIRT;
  private TileItem tileItem;

  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public TileType getTileType() {
    return tileType;
  }

  public void setTileType(TileType tileType) {
    this.tileType = tileType;
  }

  public String getSymbol() {
    return getTileType().getSymbol();
  }

  @Override
  public void render(GraphicsContext graphics) {
    graphics.setFill(getTileType().getColor());
    graphics.fillRect(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
  }


  public void setTileItem(TileItem tileItem) {
    this.tileItem = tileItem;
  }

  public TileItem getTileItem() {
    return tileItem;
  }
}
