package mocha.game.world.chunk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileFactory;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ChunkTest {

  private Chunk testObject;

  @Mock
  private Entity mockEntity;

  ChunkFactory chunkFactory;

  @Before
  public void setUp() {
    Tile[][] tiles = new Tile[6][10];
    testObject = Chunk.builder()
        .tiles(tiles)
        .build();

    chunkFactory = ChunkFactory.builder().tileFactory(new TileFactory()).build();
  }

  @Test
  public void getTile_ReturnsTileAtProperLocation() {
    Tile mockTile = mock(Tile.class);
    testObject.getTiles()[3][3] = mockTile;
    Tile expected = mockTile;
    Tile actual = testObject.getTile(3, 3);

    assertSame(expected, actual);
  }

  // region getTileAt()

  @Test
  public void getTileAt_ReturnsATile_WhenThePointIsInThatTile() throws Exception {
    String tileString = "" +
        "..." +
        "..." +
        "...";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tileString)
        .build();

    Chunk testObject = chunkFactory.read(chunkDescription);
    Tile expected = testObject.getTile(0, 0);

    Tile actual = testObject.getTileAt(0, 0).get();

    assertThat(actual).isSameAs(expected);
  }

  @Test
  public void getTileAt_ReturnsATile_WhenThePointIsStillWithinThatTile() throws Exception {
    String tileString = "" +
        "..." +
        "..." +
        "...";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tileString)
        .build();

    Chunk testObject = chunkFactory.read(chunkDescription);
    Tile expected = testObject.getTile(0, 0);

    Tile actual = testObject.getTileAt(1, 0).get();

    assertThat(actual).isSameAs(expected);
  }

  @Test
  public void getTileAt_ReturnsTheNextTile_WhenThePointIsGreaterThanTheTileSize() throws Exception {
    String tileString = "" +
        "..." +
        "..." +
        "...";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tileString)
        .build();

    Chunk testObject = chunkFactory.read(chunkDescription);
    Tile expected = testObject.getTile(1, 0);

    Tile actual = testObject.getTileAt(Tile.SIZE, 0).get();

    assertThat(actual).isSameAs(expected);
  }

  @Test
  public void getTileAt_ReturnsTheNextTile_WhenThePointIsGreaterThanTheTileSize_ForY() throws Exception {
    String tileString = "" +
        "................" +
        "................" +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tileString)
        .build();

    Chunk testObject = chunkFactory.read(chunkDescription);
    Tile expected = testObject.getTile(0, 1);

    Tile actual = testObject.getTileAt(0, Tile.SIZE).get();

    assertThat(actual).isSameAs(expected);
  }

  @Test
  public void getTileAt_ReturnsAnEmptyOptional_WhenTheCoordinateOutOfBounds() throws Exception {
    String tileString = "" +
        "..." +
        "..." +
        "...";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tileString)
        .build();

    Chunk testObject = chunkFactory.read(chunkDescription);
    int mapWidth = Chunk.SIZE * Tile.SIZE;

    Optional<Tile> actual = testObject.getTileAt(mapWidth + 1, 0);

    assertThat(actual).isEmpty();
  }
  // endregion getTileAt()
}
