package mocha.game.world;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ServerChunk;
import mocha.game.world.chunk.ServerChunkJpaRepository;

@Component
public class ServerChunkRepository extends ChunkRepository {

  @Inject
  private ServerChunkJpaRepository serverChunkJpaRepository;

  @Override
  public void put(Location location, Chunk chunk) {
    int chunkId = getIdForChunkAt(location);
    ServerChunk serverChunk = new ServerChunk(chunkId, chunk);
    ServerChunk savedChunk = serverChunkJpaRepository.save(serverChunk);
    super.put(location, savedChunk);
  }

  @Override
  public Optional<? extends Chunk> getChunkAt(Location location) {
    Optional<? extends Chunk> chunkFromMemory = super.getChunkAt(location);
    if (chunkFromMemory.isPresent()) {
      return chunkFromMemory;
    }

    int chunkId = getIdForChunkAt(location);
    Optional<ServerChunk> chunkFromRepository = serverChunkJpaRepository.findById(chunkId);
    chunkFromRepository.ifPresent(serverChunk -> super.put(location, serverChunk));
    return chunkFromRepository;
  }

}
