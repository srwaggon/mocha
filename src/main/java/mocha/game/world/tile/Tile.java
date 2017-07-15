package mocha.game.world.tile;

import mocha.gfx.Drawable;
import mocha.game.world.tile.item.TileItem;
import mocha.gfx.MochaCanvas;

public class Tile implements Drawable {

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
  public void draw(MochaCanvas mochaCanvas) {
    mochaCanvas.drawSprite(1, x * SIZE / 2, y * SIZE / 2);
  }

  public void setTileItem(TileItem tileItem) {
    this.tileItem = tileItem;
  }

  public TileItem getTileItem() {
    return tileItem;
  }
}
