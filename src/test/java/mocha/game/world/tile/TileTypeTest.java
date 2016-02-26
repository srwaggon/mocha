package mocha.game.world.tile;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTypeTest {

  @Test
  public void getSymbol_ReturnsDot_ForDirt() throws Exception {
    assertEquals(".", TileType.DIRT.getSymbol());
  }

}