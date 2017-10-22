package mocha.game.world.chunk;


import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import mocha.game.world.tile.Tile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ChunkIOTest {

  private ChunkIO testObject;

  private File dataDirectory;
  private File mapsDirectory;

  @Before
  public void setUp() throws IOException {
    testObject = new ChunkIO();

    dataDirectory = new File("data");
    FileUtils.deleteDirectory(dataDirectory);
    mapsDirectory = new File(dataDirectory, "maps");
  }

  @Test
  public void saveMap_CreatesAFileOnDiskRepresentingTheSavedMap() {
    Chunk chunk = Chunk.builder()
        .tiles(new Tile[10][10])
        .build();

    testObject.saveMap(chunk);

    File mapFile = new File(mapsDirectory, "0_0.chunk");
    assertTrue(mapFile.exists());
  }

  @Test
  public void saveMap_CreatesAMapDirectoryIfItDoesNotExist() throws IOException {
    Chunk chunk = Chunk.builder()
        .tiles(new Tile[10][10])
        .build();
    assertFalse(mapsDirectory.exists());

    testObject.saveMap(chunk);

    assertTrue(mapsDirectory.exists());
  }

}