package mocha.game.world.tile;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TileFactoryTest {

  private TileFactory testObject;

  @Before
  public void setUp() throws Exception {
    testObject = new TileFactory();
  }

  @Test
  public void tileReader_ReturnsANewGridTile() throws Exception {
    Tile actual = testObject.newTile(TileType.GRID.getSymbol());

    assertThat(actual.getTileType()).isEqualTo(TileType.GRID);
  }

  @Test
  public void tileReader_ReturnsANewDirtTile() throws Exception {
    Tile actual = testObject.newTile(".");

    assertThat(actual.getTileType()).isEqualTo(TileType.DIRT);
  }

  @Test
  public void tileReader_ReturnsANewStoneTile() throws Exception {
    Tile actual = testObject.newTile("o");

    assertThat(actual.getTileType()).isEqualTo(TileType.STONE);
  }

  @Test
  public void tileReader_ReturnsANewGrassTile() throws Exception {
    Tile actual = testObject.newTile("w");

    assertThat(actual.getTileType()).isEqualTo(TileType.GRASS);
  }

  @Test
  public void tileReader_ReturnsANewWaterTile() throws Exception {
    Tile actual = testObject.newTile("~");

    assertThat(actual.getTileType()).isEqualTo(TileType.WATER);
  }

}