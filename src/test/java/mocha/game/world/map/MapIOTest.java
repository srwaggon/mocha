package mocha.game.world.map;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapIOTest {

  private MapIO testObject;

  private File dataDirectory;
  private File mapsDirectory;


  @BeforeEach
  void setUp() throws IOException {
    testObject = new MapIO();

    dataDirectory = new File("data");
    FileUtils.deleteDirectory(dataDirectory);
    mapsDirectory = new File(dataDirectory, "maps");
  }

  @Test
  @DisplayName("saveMap() creates a file on disk representing the saved map")
  void saveMap_CreatesAFileOnDiskRepresentingTheSavedMap() {
    Map map = new Map(12, 10, 10);

    testObject.saveMap(map);

    File mapFile = new File(mapsDirectory, "12.map");
    assertTrue(mapFile.exists());
  }

  @Test
  @DisplayName("saveMap() creates a map directory if it does not exit")
  void saveMap_CreatesAMapDirectoryIfItDoesNotExist() throws IOException {
    Map map = new Map(12, 10, 10);
    assertFalse(mapsDirectory.exists());

    testObject.saveMap(map);

    assertTrue(mapsDirectory.exists());
  }

}