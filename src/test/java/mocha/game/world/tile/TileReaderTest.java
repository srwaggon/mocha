package mocha.game.world.tile;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TileReaderTest {

  private TileReader testObject;

  @Before
  public void setUp() throws Exception {
    testObject = new TileReader();
  }

  @Test
  public void tileReader_ReturnsANewGridTile() throws Exception {
    Tile actual = testObject.read(TileType.GRID.getSymbol());

    assertThat(actual.getTileType()).isEqualTo(TileType.GRID);
  }

  @Test
  public void tileReader_ReturnsANewDirtTile() throws Exception {
    Tile actual = testObject.read(".");

    assertThat(actual.getTileType()).isEqualTo(TileType.DIRT);
  }

  @Test
  public void tileReader_ReturnsANewStoneTile() throws Exception {
    Tile actual = testObject.read("o");

    assertThat(actual.getTileType()).isEqualTo(TileType.STONE);
  }

  @Test
  public void tileReader_ReturnsANewGrassTile() throws Exception {
    Tile actual = testObject.read("w");

    assertThat(actual.getTileType()).isEqualTo(TileType.GRASS);
  }

  @Test
  public void tileReader_ReturnsANewWaterTile() throws Exception {
    Tile actual = testObject.read("~");

    assertThat(actual.getTileType()).isEqualTo(TileType.WATER);
  }

}