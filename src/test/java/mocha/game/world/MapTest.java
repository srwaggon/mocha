package mocha.game.world;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.Mob;
import mocha.game.world.tile.Tile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MapTest {

  private Map subject;

  @Before public void setUp() {
    subject = new Map(0, 10, 6);
  }

  @Test public void getTiles_ReturnsAllTiles() {
    Tile[][] tiles = subject.getTiles();

    assertEquals(6, tiles.length);
    assertEquals(10, tiles[0].length);
  }

  @Test public void getTile_ReturnsTileAtProperLocation() {
    Tile[][] tiles = subject.getTiles();
    Tile expected = tiles[3][3];
    Tile actual = subject.getTile(3, 3);

    assertSame(expected, actual);
  }

  @Test public void getEntities_ReturnsMappingOfEntitiesById() {
    Mob expected = new Mob(1);
    subject.addEntity(expected);

    Entity actual = subject.getEntities().get(expected.getId());

    assertSame(expected, actual);
  }

  @Test
  public void getColumnCount_ReturnsCountOfColumns() {
    assertEquals(10, subject.getColumnCount());
  }

  @Test
  public void getRowCount_ReturnsCountOfRows() {
    assertEquals(6, subject.getRowCount());
  }
}
