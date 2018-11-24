package mocha;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileSetFactory;
import mocha.shared.Repository;

@Component
public class ServerSetup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private Repository<Chunk, Integer> chunkRepository;

  @Inject
  private TileSetFactory tileSetFactory;

  @Inject
  private ChunkService chunkService;

  @Override
  public void run(String... args) {
    createChunks();
  }

  private void addEntity() {
    game.addEntity(newRandomEntity());
  }

  private Entity newRandomEntity() {
    return new Entity(null, new Location(random(), random()));
  }

  private int random() {
    return new Random().nextInt(16) * 32;
  }

  private void createChunks() {
    int radius = 0;
    for (int y = -radius; y <= radius; y++) {
      for (int x = -radius; x <= radius; x++) {
        chunkService.getChunkByIndex(new Location(x, y));
      }
    }
  }
}
