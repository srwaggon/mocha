package mocha.game.world.tile;

import org.junit.Test;

import mocha.game.world.tile.item.Stone;
import mocha.game.world.tile.item.TileItem;

import static org.junit.Assert.*;

public class TileTest {

  @Test
  public void tileConstructor_ConstructsDirtTileByDefault() {
    assertEquals(TileType.DIRT, new Tile().getTileType());
  }

  @Test
  public void getTileItem_ReturnsHeldTileItem() {
    Tile testObject = new Tile();
    TileItem tileItem = new Stone();

    testObject.setTileItem(tileItem);

    assertEquals(tileItem, testObject.getTileItem());
  }

}