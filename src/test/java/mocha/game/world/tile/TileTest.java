package mocha.game.world.tile;

import org.junit.Test;

import mocha.game.world.tile.item.Stone;
import mocha.game.world.tile.item.TileItem;

import static org.junit.Assert.*;

public class TileTest {

  @Test
  public void tileConstructor_ConstructsDirtTileByDefault() {
    assertEquals(TileType.DIRT, new Tile(0, 0).getTileType());
  }

  @Test
  public void getSymbol_ReturnsTileTypeSymbol() {
    Tile testObject = new Tile(0, 0);
    TileType type = TileType.GRASS;

    testObject.setTileType(type);

    assertEquals(type.getSymbol(), testObject.getSymbol());
  }

  @Test
  public void getTileItem_ReturnsHeldTileItem() {
    Tile testObject = new Tile(0, 0);
    TileItem tileItem = new Stone();

    testObject.setTileItem(tileItem);

    assertEquals(tileItem, testObject.getTileItem());
  }

}