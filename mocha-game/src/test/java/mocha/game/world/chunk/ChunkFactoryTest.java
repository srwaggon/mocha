package mocha.game.world.chunk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import mocha.game.world.tile.TileReader;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileType;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ChunkFactoryTest {

  private ChunkFactory testObject;

  @Before
  public void setUp() {
    TileReader tileReader = new TileReader();
    TileSetFactory tileSetFactory = new TileSetFactory(tileReader);
    testObject = new ChunkFactory(tileSetFactory);
  }

  @Test
  public void read_ProducesABaseMap_FromADescription() {
    ChunkDescription chunkDescription = new ChunkDescription(
        "" +
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
        );

    Chunk readChunk = testObject.read(chunkDescription);

    for (int i = 0; i < Chunk.SIZE; i++) {
      for (int j = 0; j < Chunk.SIZE; j++) {
        assertThat(readChunk.getTile(j, i)).isEqualTo(TileType.GRID);
      }
    }
  }

  @Test
  public void read_idkTilesInTheRightPlacesButThisMessageIsBetter() {
    // grid, grass, stone, grass
    ChunkDescription chunkDescription = new ChunkDescription("x.ow");

    Chunk actual = testObject.read(chunkDescription);

    assertThat(actual.getTile(0, 0)).isEqualTo(TileType.GRID);
    assertThat(actual.getTile(1, 0)).isEqualTo(TileType.DIRT);
    assertThat(actual.getTile(2, 0)).isEqualTo(TileType.STONE);
    assertThat(actual.getTile(3, 0)).isEqualTo(TileType.GRASS);
  }

}