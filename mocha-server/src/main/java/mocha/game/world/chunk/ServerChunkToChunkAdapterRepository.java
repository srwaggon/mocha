package mocha.game.world.chunk;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;

import static com.google.common.collect.Lists.newArrayList;

@org.springframework.stereotype.Repository
public class ServerChunkToChunkAdapterRepository implements Repository<Chunk, Integer> {

  @Inject
  private ServerChunkJpaRepository serverChunkJpaRepository;

  @Override
  public List<Chunk> findAll() {
    return newArrayList(serverChunkJpaRepository.findAll());
  }

  @Override
  public Chunk save(Chunk chunk) {
    ServerChunk serverChunk = new ServerChunk(chunk.getId(), chunk.getLocation(), chunk.getTiles());
    return serverChunkJpaRepository.save(serverChunk);
  }

  @Override
  public Optional<Chunk> findById(Integer chunkId) {
    Optional<ServerChunk> byId = serverChunkJpaRepository.findById(chunkId);
    //noinspection OptionalIsPresent
    return byId.isPresent() ? Optional.of(byId.get()) : Optional.empty();
  }

  @Override
  public void delete(Chunk chunk) {
    serverChunkJpaRepository.findById(chunk.getId())
        .ifPresent(serverChunkJpaRepository::delete);
  }

  @Override
  public void deleteAll() {
    serverChunkJpaRepository.deleteAll();
  }
}
