package mocha.game.world.map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import mocha.game.world.tile.TileFactory;
import mocha.game.world.tile.TileType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MapReaderTest {

  private MapReader testObject;

  @Spy
  private TileFactory tileFactory = new TileFactory();

  @Before
  public void setUp() throws Exception {
    testObject = MapReader.builder()
        .tileFactory(tileFactory)
        .build();
  }

  @Test
  public void read_ProducesABaseMap_FromADescription() throws Exception {
    MapDescription mapDescription = MapDescription.builder()
        .id(17)
        .columns(3)
        .rows(3)
        .tiles("xxxxxxxxx")
        .build();

    Map readMap = testObject.read(mapDescription);

    assertThat(readMap.getId()).isEqualTo(17);
    assertThat(readMap.getRowCount()).isEqualTo(3);
    assertThat(readMap.getColumnCount()).isEqualTo(3);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertThat(readMap.getTile(j, i).getTileType()).isEqualTo(TileType.GRID);
      }
    }
  }

  @Test
  public void read_DelegatesToTheTileReaderForTiles() throws Exception {
    MapDescription mapDescription = MapDescription.builder()
        .id(17)
        .columns(1)
        .rows(1)
        .tiles(" ")
        .build();

    testObject.read(mapDescription);

    verify(tileFactory).newTile(" ");
  }

  @Test
  public void read_idkTilesInTheRightPlacesButThisMessageIsBetter() throws Exception {
    MapDescription mapDescription = MapDescription.builder()
        .id(17)
        .columns(2)
        .rows(2)
        .tiles("x.ow") // grid, grass, stone, grass
        .build();

    Map actual = testObject.read(mapDescription);

    assertThat(actual.getTile(0, 0).getTileType()).isEqualTo(TileType.GRID);
    assertThat(actual.getTile(1, 0).getTileType()).isEqualTo(TileType.DIRT);
    assertThat(actual.getTile(0, 1).getTileType()).isEqualTo(TileType.STONE);
    assertThat(actual.getTile(1, 1).getTileType()).isEqualTo(TileType.GRASS);
  }
}