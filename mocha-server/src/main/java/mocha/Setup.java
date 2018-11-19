package mocha;

import com.google.common.collect.Sets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.ServerChunk;
import mocha.game.world.chunk.ServerChunkRepository;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileType;

import static mocha.game.world.ServerWorld.getIdForChunkIndex;

@Component
public class Setup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private EntityFactory entityFactory;

  @Inject
  private ServerChunkRepository serverChunkRepository;

  @Inject
  private TileSetFactory tileSetFactory;

  @Override
  public void run(String... args) {
    createChunks();
    game.addEntity(entityFactory.newRandomSlider().at(128, 128));
    game.addEntity(entityFactory.newRandomSlider().at(192, 192));
    game.addEntity(entityFactory.newRandomSlider().at(384, 384));
    game.addEntity(entityFactory.newPickaxe().at(64, 64));
  }

  private void createChunks() {
    for (int y = -16; y < 16; y++) {
      for (int x = -16; x < 16; x++) {
        newServerChunkIfMissing(y, x);
      }
    }
  }

  private void newServerChunkIfMissing(int y, int x) {
    Location chunkIndex = new Location(x, y);
    int chunkId = getIdForChunkIndex(chunkIndex);
    ServerChunk chunk = serverChunkRepository.findOne(chunkId);
    if (chunk == null) {
      newServerChunk(chunkId);
    }
  }

  private void newServerChunk(int chunkId) {
    TileType[] tiles = tileSetFactory.createRandomTiles();
    HashSet<Entity> entities = Sets.newHashSet();
    ServerChunk serverChunk = new ServerChunk(chunkId, tiles, entities);
    serverChunkRepository.save(serverChunk);
  }

}
