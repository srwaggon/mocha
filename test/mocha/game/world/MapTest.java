package mocha.game.world;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapTest {

  @Before
  public void setUp() {

  }

  @Test
  public void getTiles_ReturnsAllTiles() {
    Map testObject = new Map(10, 6);

    Tile[][] tiles = testObject.getTiles();

    assertEquals(10, tiles.length);
    assertEquals(6, tiles[0].length);
  }
}
