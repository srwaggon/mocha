package mocha;

import com.google.common.collect.Sets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ServerChunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileType;
import mocha.shared.Repository;

import static mocha.game.world.chunk.Chunk.getIdForChunkIndex;

@Component
public class ServerSetup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private Repository<Chunk, Integer> chunkRepository;

  @Inject
  private TileSetFactory tileSetFactory;

  @Override
  public void run(String... args) {
    createChunks();
    addEntity();
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
    for (int y = -16; y < 16; y++) {
      for (int x = -16; x < 16; x++) {
        Location chunkIndex = new Location(x, y);
        int chunkId = getIdForChunkIndex(chunkIndex);
        if (!chunkRepository.findById(chunkId).isPresent()) {
          chunkRepository.save(newServerChunk(chunkIndex));
        }
      }
    }
  }

  private ServerChunk newServerChunk(Location chunkIndex) {
    int chunkId = getIdForChunkIndex(chunkIndex);
    return newServerChunk(chunkId);
  }

  private ServerChunk newServerChunk(int chunkId) {
    TileType[] tiles = tileSetFactory.createRandomTiles();
    HashSet<Entity> entities = Sets.newHashSet();
    return new ServerChunk(chunkId, tiles, entities);
  }

}
