package mocha.game.world;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.Mob;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MapTest {

  private Map testObject;

  @Before public void setUp() {
    testObject = new Map(0, 10, 6);
  }

  @Test public void getTiles_ReturnsAllTiles() {
    Tile[][] tiles = testObject.getTiles();

    assertEquals(10, tiles.length);
    assertEquals(6, tiles[0].length);
  }

  @Test public void getTile_ReturnsTileAtProperLocation() {
    Tile[][] tiles = testObject.getTiles();
    Tile expected = tiles[3][3];
    Tile actual = testObject.getTile(3, 3);

    assertSame(expected, actual);
  }

  @Test public void getEntities_ReturnsMappingOfEntitiesById() {
    Mob expected = new Mob(1);
    testObject.addEntity(expected);

    Entity actual = testObject.getEntities().get(expected.getId());

    assertSame(expected, actual);
  }
}
