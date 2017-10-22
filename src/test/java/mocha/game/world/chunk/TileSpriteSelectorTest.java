package mocha.game.world.chunk;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.tile.TileFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class TileSpriteSelectorTest {

  private TileSpriteSelector testObject;

  private TileFactory tileFactory;
  private ChunkReader chunkReader;

  @Before
  public void setUp() throws Exception {
    tileFactory = new TileFactory();
    chunkReader = ChunkReader.builder().tileFactory(tileFactory).build();
    testObject = new TileSpriteSelector();
  }

  @Test
  public void grass_withDirtAllAround() throws Exception {
    String tilesString = "" +
        "..." +
        ".w." +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(160);
  }

  @Test
  public void grass_withGrassNorthOnly() throws Exception {
    String tilesString = "" +
        ".w." +
        ".w." +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(161);
  }

  @Test
  public void grass_withGrassEastOnly() throws Exception {
    String tilesString = "" +
        "..." +
        ".ww" +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(162);
  }

  @Test
  public void grass_withGrassNorthAndEast() throws Exception {
    String tilesString = "" +
        ".w." +
        ".ww" +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(163);
  }

  @Test
  public void grass_withGrassSouthOnly() throws Exception {
    String tilesString = "" +
        "..." +
        ".w." +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(164);
  }

  @Test
  public void grass_withGrassNorthAndSouth() throws Exception {
    String tilesString = "" +
        ".w." +
        ".w." +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(165);
  }

  @Test
  public void grass_withGrassEastAndSouth() throws Exception {
    String tilesString = "" +
        "..." +
        ".ww" +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(166);
  }

  @Test
  public void grass_withGrassNorthAndEastAndSouth() throws Exception {
    String tilesString = "" +
        ".w." +
        ".ww" +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(167);
  }

  @Test
  public void grass_withGrassWestOnly() throws Exception {
    String tilesString = "" +
        "..." +
        "ww." +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(168);
  }

  @Test
  public void grass_withGrassNorthAndWest() throws Exception {
    String tilesString = "" +
        ".w." +
        "ww." +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(169);
  }

  @Test
  public void grass_withGrassEastAndWest() throws Exception {
    String tilesString = "" +
        "..." +
        "www" +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(170);
  }

  @Test
  public void grass_withGrassNorthAndEastAndWest() throws Exception {
    String tilesString = "" +
        ".w." +
        "www" +
        "...";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(171);
  }

  @Test
  public void grass_withGrassSouthAndWest() throws Exception {
    String tilesString = "" +
        "..." +
        "ww." +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(172);
  }

  @Test
  public void grass_withGrassNorthAndSouthAndWest() throws Exception {
    String tilesString = "" +
        ".w." +
        "ww." +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(173);
  }

  @Test
  public void grass_withGrassEastAndSouthAndWest() throws Exception {
    String tilesString = "" +
        "..." +
        "www" +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(174);
  }

  @Test
  public void grass_withGrassOnAllSides() throws Exception {
    String tilesString = "" +
        ".w." +
        "www" +
        ".w.";
    Chunk chunk = chunkReader.read(ChunkDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(175);
  }

}