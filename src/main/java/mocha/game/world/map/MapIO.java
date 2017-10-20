package mocha.game.world.map;

import com.google.common.io.Files;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MapIO {

  private File dataDirectory = new File("data");
  private File mapsDirectory = new File(dataDirectory, "maps");

  void saveMap(Map map) {

    ensureMapsDirectory();

    File file = new File(mapsDirectory, map.getId() + ".map");

    try {
      Files.touch(file);
    } catch (IOException ioException) {
      log.error("Error occurred while creating map file on disk.", ioException);
    }
  }

  private void ensureMapsDirectory() {
    if (!mapsDirectory.exists()) {
      mapsDirectory.mkdirs();
    }
  }
}
