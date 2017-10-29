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
public class ChunkFactoryTest {

  private ChunkFactory testObject;

  @Spy
  private TileFactory tileFactory = new TileFactory();

  @Before
  public void setUp() throws Exception {
    testObject = ChunkFactory.builder()
        .tileFactory(tileFactory)
        .build();
  }

  @Test
  public void read_ProducesABaseMap_FromADescription() throws Exception {
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles("" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx" +
            "xxxxxxxxxxxxxxxx"
        )
        .build();

    Chunk readChunk = testObject.read(chunkDescription);

    for (int i = 0; i < Chunk.SIZE; i++) {
      for (int j = 0; j < Chunk.SIZE; j++) {
        assertThat(readChunk.getTile(j, i)).isEqualTo(TileType.GRID);
      }
    }
  }

  @Test
  public void read_DelegatesToTheTileReaderForTiles() throws Exception {
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(" ")
        .build();

    testObject.read(chunkDescription);

    verify(tileFactory).newTile(" ");
  }

  @Test
  public void read_idkTilesInTheRightPlacesButThisMessageIsBetter() throws Exception {
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles("x.ow") // grid, grass, stone, grass
        .build();

    Chunk actual = testObject.read(chunkDescription);

    assertThat(actual.getTile(0, 0)).isEqualTo(TileType.GRID);
    assertThat(actual.getTile(1, 0)).isEqualTo(TileType.DIRT);
    assertThat(actual.getTile(2, 0)).isEqualTo(TileType.STONE);
    assertThat(actual.getTile(3, 0)).isEqualTo(TileType.GRASS);
  }

}