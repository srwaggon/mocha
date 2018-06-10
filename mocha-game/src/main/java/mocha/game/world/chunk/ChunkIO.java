package mocha.game.world.chunk;

import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ChunkIO {

  private Logger log = LoggerFactory.getLogger(ChunkIO.class);
  private File dataDirectory = new File("data");
  private File mapsDirectory = new File(dataDirectory, "maps");

  void saveMap(Chunk chunk) {

    ensureMapsDirectory();

    File file = new File(mapsDirectory, "0_0.chunk");

    try {
      Files.touch(file);
    } catch (IOException ioException) {
      log.error("Error occurred while creating chunk file on disk.", ioException);
    }
  }

  private void ensureMapsDirectory() {
    if (!mapsDirectory.exists()) {
      mapsDirectory.mkdirs();
    }
  }
}
