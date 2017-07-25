package mocha.game.world.tile;

import lombok.Data;
import mocha.game.world.tile.item.TileItem;

@Data
public class Tile {

  public static final int SIZE = 32;
  private TileType tileType = TileType.DIRT;
  private TileItem tileItem;

  public void setTileItem(TileItem tileItem) {
    this.tileItem = tileItem;
  }

  public TileItem getTileItem() {
    return tileItem;
  }
}
