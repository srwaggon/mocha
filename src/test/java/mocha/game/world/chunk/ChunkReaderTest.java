package mocha.game.world.chunk;

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
public class ChunkReaderTest {

  private ChunkReader testObject;

  @Spy
  private TileFactory tileFactory = new TileFactory();

  @Before
  public void setUp() throws Exception {
    testObject = ChunkReader.builder()
        .tileFactory(tileFactory)
        .build();
  }

  @Test
  public void read_ProducesABaseMap_FromADescription() throws Exception {
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .id(17)
        .columns(3)
        .rows(3)
        .tiles("xxxxxxxxx")
        .build();

    Chunk readChunk = testObject.read(chunkDescription);

    assertThat(readChunk.getId()).isEqualTo(17);
    assertThat(readChunk.getRowCount()).isEqualTo(3);
    assertThat(readChunk.getColumnCount()).isEqualTo(3);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertThat(readChunk.getTile(j, i).getTileType()).isEqualTo(TileType.GRID);
      }
    }
  }

  @Test
  public void read_DelegatesToTheTileReaderForTiles() throws Exception {
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .id(17)
        .columns(1)
        .rows(1)
        .tiles(" ")
        .build();

    testObject.read(chunkDescription);

    verify(tileFactory).newTile(" ");
  }

  @Test
  public void read_idkTilesInTheRightPlacesButThisMessageIsBetter() throws Exception {
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .id(17)
        .columns(2)
        .rows(2)
        .tiles("x.ow") // grid, grass, stone, grass
        .build();

    Chunk actual = testObject.read(chunkDescription);

    assertThat(actual.getTile(0, 0).getTileType()).isEqualTo(TileType.GRID);
    assertThat(actual.getTile(1, 0).getTileType()).isEqualTo(TileType.DIRT);
    assertThat(actual.getTile(0, 1).getTileType()).isEqualTo(TileType.STONE);
    assertThat(actual.getTile(1, 1).getTileType()).isEqualTo(TileType.GRASS);
  }
}