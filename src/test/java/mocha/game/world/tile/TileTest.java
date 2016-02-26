package mocha.game.world.tile;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

  @Test
  public void tileConstructor_ConstructsDirtTileByDefault() {
    assertEquals(TileType.DIRT, new Tile().getTileType());
  }

  @Test
  public void getTileType_ReturnsTheTypeThatWasSet_UponConstruction() {
    TileType type = TileType.DIRT;

    assertEquals(type, new Tile(type).getTileType());
  }

}