package mocha.game.world.chunk;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import mocha.shared.Repository;

@org.springframework.stereotype.Repository
public class ServerChunkToChunkAdapterRepository implements Repository<Chunk, Integer> {

  @Inject
  private ServerChunkJpaRepository serverChunkJpaRepository;

  @Override
  public List<Chunk> findAll() {
    return Lists.newArrayList(serverChunkJpaRepository.findAll());
  }

  @Override
  public Chunk save(Chunk chunk) {
    return serverChunkJpaRepository.save(new ServerChunk(chunk.getId(), chunk.getLocation(), chunk.getTiles()));
  }

  @Override
  public Optional<Chunk> findById(Integer chunkId) {
    Optional<ServerChunk> byId = serverChunkJpaRepository.findById(chunkId);
    //noinspection OptionalIsPresent
    return byId.isPresent() ? Optional.of(byId.get()) : Optional.empty();
  }

  @Override
  public void delete(Chunk element) {
    serverChunkJpaRepository.findById(element.getId())
        .ifPresent(serverChunkJpaRepository::delete);
  }

  @Override
  public void deleteAll() {
    serverChunkJpaRepository.deleteAll();
  }
}
