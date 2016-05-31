package mocha.game.world.tile;

import org.junit.Test;

import mocha.game.gfx.Colors;

import static org.junit.Assert.*;

public class TileTypeTest {

  @Test
  public void getSymbol_ReturnsDot_ForDirt() throws Exception {
    assertEquals(".", TileType.DIRT.getSymbol());
  }

  @Test
  public void getColor_ReturnsBrown_ForDirt() throws Exception {
    assertEquals(Colors.DIRT_BROWN, TileType.DIRT.getColor());
  }

}