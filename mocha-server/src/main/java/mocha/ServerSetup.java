package mocha;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.ServerChunk;
import mocha.game.world.chunk.ServerChunkJpaRepository;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileType;

import static mocha.game.world.chunk.Chunk.getIdForChunkIndex;

@Component
public class ServerSetup implements CommandLineRunner {

  @Inject
  private Game game;

  @Inject
  private ServerChunkJpaRepository serverChunkJpaRepository;

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
    return new Entity().at(random(), random());
  }

  private int random() {
    return new Random().nextInt(16) * 32;
  }

  private void createChunks() {
    List<ServerChunk> serverChunks = Lists.newArrayList();
    for (int y = -16; y < 16; y++) {
      for (int x = -16; x < 16; x++) {
        Location chunkIndex = new Location(x, y);
        if (!getServerChunk(chunkIndex).isPresent()) {
          ServerChunk serverChunk = newServerChunk(chunkIndex);
          serverChunks.add(serverChunk);
        }
      }
    }
    serverChunkJpaRepository.saveAll(serverChunks);
  }

  private Optional<ServerChunk> getServerChunk(Location chunkIndex) {
    int chunkId = getIdForChunkIndex(chunkIndex);
    return serverChunkJpaRepository.findById(chunkId);
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
