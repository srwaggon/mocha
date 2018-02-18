package mocha.gfx;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkDescription;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.tile.TileFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class TileSpriteSelectorTest {

  private TileSpriteSelector testObject;

  private TileFactory tileFactory;
  private ChunkFactory chunkFactory;

  @Before
  public void setUp() {
    tileFactory = new TileFactory();
    chunkFactory = ChunkFactory.builder().tileFactory(tileFactory).build();
    testObject = new TileSpriteSelector();
  }

  @Test
  public void grass_withDirtAllAround() {
    String tilesString = "" +
        "................" +
        ".w.............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(0);
  }

  @Test
  public void grass_withGrassNorthOnly() {
    String tilesString = "" +
        ".w.............." +
        ".w.............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(1);
  }

  @Test
  public void grass_withGrassEastOnly() {
    String tilesString = "" +
        "................" +
        ".ww............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(2);
  }

  @Test
  public void grass_withGrassNorthAndEast() {
    String tilesString = "" +
        ".w.............." +
        ".ww............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(3);
  }

  @Test
  public void grass_withGrassSouthOnly() {
    String tilesString = "" +
        "................" +
        ".w.............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(4);
  }

  @Test
  public void grass_withGrassNorthAndSouth() {
    String tilesString = "" +
        ".w.............." +
        ".w.............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(5);
  }

  @Test
  public void grass_withGrassEastAndSouth() {
    String tilesString = "" +
        "................" +
        ".ww............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(6);
  }

  @Test
  public void grass_withGrassNorthAndEastAndSouth() {
    String tilesString = "" +
        ".w.............." +
        ".ww............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(7);
  }

  @Test
  public void grass_withGrassWestOnly() {
    String tilesString = "" +
        "................" +
        "ww.............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(8);
  }

  @Test
  public void grass_withGrassNorthAndWest() {
    String tilesString = "" +
        ".w.............." +
        "ww.............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(9);
  }

  @Test
  public void grass_withGrassEastAndWest() {
    String tilesString = "" +
        "................" +
        "www............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(10);
  }

  @Test
  public void grass_withGrassNorthAndEastAndWest() {
    String tilesString = "" +
        ".w.............." +
        "www............." +
        "................";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(11);
  }

  @Test
  public void grass_withGrassSouthAndWest() {
    String tilesString = "" +
        "................" +
        "ww.............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(12);
  }

  @Test
  public void grass_withGrassNorthAndSouthAndWest() {
    String tilesString = "" +
        ".w.............." +
        "ww.............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(13);
  }

  @Test
  public void grass_withGrassEastAndSouthAndWest() {
    String tilesString = "" +
        "................" +
        "www............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(14);
  }

  @Test
  public void grass_withGrassOnAllSides() {
    String tilesString = "" +
        ".w.............." +
        "www............." +
        ".w..............";
    ChunkDescription chunkDescription = ChunkDescription.builder()
        .tiles(tilesString)
        .build();
    Chunk chunk = chunkFactory.read(chunkDescription);

    assertThat(testObject.selectSprite(chunk, 1, 1)).isEqualTo(15);
  }

}