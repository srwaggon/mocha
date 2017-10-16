package mocha.game.world;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.Mob;
import mocha.game.world.map.Map;
import mocha.game.world.tile.Tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MapTest {

  private Map testObject;

  @Mock
  private Entity mockEntity;

  @Before
  public void setUp() {
    testObject = new Map(0, 10, 6);
  }

  @Test
  public void getTiles_ReturnsAllTiles() {
    Tile[][] tiles = testObject.getTiles();

    assertEquals(6, tiles.length);
    assertEquals(10, tiles[0].length);
  }

  @Test
  public void getTile_ReturnsTileAtProperLocation() {
    Tile[][] tiles = testObject.getTiles();
    Tile expected = tiles[3][3];
    Tile actual = testObject.getTile(3, 3);

    assertSame(expected, actual);
  }

  @Test
  public void getColumnCount_ReturnsCountOfColumns() {
    assertEquals(10, testObject.getColumnCount());
  }

  @Test
  public void getRowCount_ReturnsCountOfRows() {
    assertEquals(6, testObject.getRowCount());
  }

  @Test
  public void getEntities_StartsEmpty() {
    Set<Entity> actual = testObject.getEntities();

    assertThat(actual).isEmpty();
  }

  // region add()

  @Test
  public void add_TracksTheEntity() throws Exception {
    Mob expected = new Mob(1);

    testObject.add(expected);

    Set<Entity> entities = testObject.getEntities();
    assertThat(entities).hasSize(1);
    assertThat(entities).containsExactlyInAnyOrder(expected);
  }

  @Test
  public void add_DoesNotAllowDuplicates() throws Exception {
    Entity onlyOnce = new Entity();

    testObject.add(onlyOnce);
    testObject.add(onlyOnce);

    Set<Entity> entities = testObject.getEntities();
    assertThat(entities).hasSize(1);
    assertThat(entities).containsExactlyInAnyOrder(onlyOnce);
  }

  @Test
  public void add_CanTrackMoreThanOneEntity() throws Exception {
    Mob expected0 = new Mob(0);
    Mob expected1 = new Mob(1);
    Mob expected2 = new Mob(2);

    testObject.add(expected0);
    testObject.add(expected1);
    testObject.add(expected2);

    Set<Entity> entities = testObject.getEntities();
    assertThat(entities).hasSize(3);
    assertThat(entities).containsExactlyInAnyOrder(expected0, expected1, expected2);
  }

  @Test
  public void add_InformsTheEntity() throws Exception {
    testObject.add(mockEntity);

    verify(mockEntity).setMapId(eq(testObject.getId()));
  }

  // endregion add()

  // region remove()

  @Test
  public void remove_RemovesTheEntityFromTheMap() throws Exception {
    Entity entity = new Entity();
    testObject.add(entity);

    testObject.remove(entity);

    Set<Entity> entities = testObject.getEntities();
    assertThat(entities).isEmpty();
  }

  @Test
  public void remove_RemovesONLYTheSuppliedEntity() throws Exception {
    Entity remains = new Entity();
    Entity toBeRemoved = new Entity();
    testObject.add(remains);
    testObject.add(toBeRemoved);

    testObject.remove(toBeRemoved);

    Set<Entity> entities = testObject.getEntities();
    assertThat(entities).isNotEmpty();
    assertThat(entities).doesNotContain(toBeRemoved);
    assertThat(entities).containsExactlyInAnyOrder(remains);
  }
  // endregion remove()
}
