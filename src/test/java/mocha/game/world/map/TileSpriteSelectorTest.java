package mocha.game.world.map;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.tile.TileReader;

import static org.assertj.core.api.Assertions.assertThat;

public class TileSpriteSelectorTest {

  private TileSpriteSelector testObject;

  private TileReader tileReader;
  private MapReader mapReader;

  @Before
  public void setUp() throws Exception {
    tileReader = new TileReader();
    mapReader = MapReader.builder().tileReader(tileReader).build();
    testObject = new TileSpriteSelector();
  }

  @Test
  public void grass_withDirtAllAround() throws Exception {
    String tilesString = "" +
        "..." +
        ".w." +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(160);
  }

  @Test
  public void grass_withGrassNorthOnly() throws Exception {
    String tilesString = "" +
        ".w." +
        ".w." +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(161);
  }

  @Test
  public void grass_withGrassEastOnly() throws Exception {
    String tilesString = "" +
        "..." +
        ".ww" +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(162);
  }

  @Test
  public void grass_withGrassNorthAndEast() throws Exception {
    String tilesString = "" +
        ".w." +
        ".ww" +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(163);
  }

  @Test
  public void grass_withGrassSouthOnly() throws Exception {
    String tilesString = "" +
        "..." +
        ".w." +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(164);
  }

  @Test
  public void grass_withGrassNorthAndSouth() throws Exception {
    String tilesString = "" +
        ".w." +
        ".w." +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(165);
  }

  @Test
  public void grass_withGrassEastAndSouth() throws Exception {
    String tilesString = "" +
        "..." +
        ".ww" +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(166);
  }

  @Test
  public void grass_withGrassNorthAndEastAndSouth() throws Exception {
    String tilesString = "" +
        ".w." +
        ".ww" +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(167);
  }

  @Test
  public void grass_withGrassWestOnly() throws Exception {
    String tilesString = "" +
        "..." +
        "ww." +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(168);
  }

  @Test
  public void grass_withGrassNorthAndWest() throws Exception {
    String tilesString = "" +
        ".w." +
        "ww." +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(169);
  }

  @Test
  public void grass_withGrassEastAndWest() throws Exception {
    String tilesString = "" +
        "..." +
        "www" +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(170);
  }

  @Test
  public void grass_withGrassNorthAndEastAndWest() throws Exception {
    String tilesString = "" +
        ".w." +
        "www" +
        "...";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(171);
  }

  @Test
  public void grass_withGrassSouthAndWest() throws Exception {
    String tilesString = "" +
        "..." +
        "ww." +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(172);
  }

  @Test
  public void grass_withGrassNorthAndSouthAndWest() throws Exception {
    String tilesString = "" +
        ".w." +
        "ww." +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(173);
  }

  @Test
  public void grass_withGrassEastAndSouthAndWest() throws Exception {
    String tilesString = "" +
        "..." +
        "www" +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(174);
  }

  @Test
  public void grass_withGrassOnAllSides() throws Exception {
    String tilesString = "" +
        ".w." +
        "www" +
        ".w.";
    Map map = mapReader.read(MapDescription.builder().id(0).columns(3).rows(3).tiles(tilesString).build());

    assertThat(testObject.selectSprite(map, 1, 1)).isEqualTo(175);
  }

}