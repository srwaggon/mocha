package mocha.game.world.chunk;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import mocha.game.world.Location;
import mocha.game.world.tile.TileType;
import mocha.shared.Repository;

public class ChunkService {

  private ChunkFactory chunkFactory;
  private Repository<Chunk, Integer> chunkRepository;

  public ChunkService(
      ChunkFactory chunkFactory,
      Repository<Chunk, Integer> chunkRepository
  ) {
    this.chunkFactory = chunkFactory;
    this.chunkRepository = chunkRepository;
  }

  public Chunk getChunkAt(Location location) {
    int chunkId = ChunkIdHelper.getIdForChunkAt(location);
    return getChunkById(chunkId);
  }

  public Chunk getChunkByIndex(Location chunkIndex) {
    int chunkId = ChunkIdHelper.getIdForChunkIndex(chunkIndex);
    return getChunkById(chunkId);
  }

  public Chunk getChunkById(int chunkId) {
    return chunkRepository.findById(chunkId).orElseGet(() ->
        chunkRepository.save(chunkFactory.newRandomChunk(chunkId)));
  }

  public List<TileType> getTileNeighbors(Location location) {
    List<TileType> results = Lists.newArrayList();
    getTileAt(location.north()).ifPresent(results::add);
    getTileAt(location.east()).ifPresent(results::add);
    getTileAt(location.south()).ifPresent(results::add);
    getTileAt(location.west()).ifPresent(results::add);
    return results;
  }

  public Optional<TileType> getTileAt(Location location) {
    return getChunkAt(location).getTileAt(location);
  }

}
