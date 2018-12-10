package mocha.game.world.chunk;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.chunk.tile.TileType;
import mocha.shared.Repository;

import static mocha.game.world.Direction.EAST;
import static mocha.game.world.Direction.NORTH;
import static mocha.game.world.Direction.SOUTH;
import static mocha.game.world.Direction.WEST;

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

  public Chunk getOrCreateChunkAt(Location location) {
    int chunkId = ChunkIdHelper.getIdForChunkAt(location);
    return getOrCreateChunkById(chunkId);
  }

  public Chunk getOrCreateChunkByIndex(Location chunkIndex) {
    int chunkId = ChunkIdHelper.getIdForChunkIndex(chunkIndex);
    return getOrCreateChunkById(chunkId);
  }

  public Chunk getOrCreateChunkById(int chunkId) {
    return chunkRepository.findById(chunkId).orElseGet(() ->
        chunkRepository.save(chunkFactory.newRandomChunk(chunkId)));
  }

  public Map<Direction, TileType> getTileNeighborsInExistingChunks(Location location) {
    Map<Direction, TileType> neighbors = Maps.newEnumMap(Direction.class);
    putTileNeighborInExistingChunk(location, NORTH, neighbors);
    putTileNeighborInExistingChunk(location, EAST, neighbors);
    putTileNeighborInExistingChunk(location, SOUTH, neighbors);
    putTileNeighborInExistingChunk(location, WEST, neighbors);
    return neighbors;
  }

  private void putTileNeighborInExistingChunk(Location location, Direction direction, Map<Direction, TileType> neighbors) {
    getTileNeighborInExistingChunk(location, direction).ifPresent(tileType -> neighbors.put(direction, tileType));
  }

  private Optional<TileType> getTileNeighborInExistingChunk(Location location, Direction direction) {
    Location directionLocation = location.from(direction);
    int chunkId = ChunkIdHelper.getIdForChunkAt(directionLocation);
    return chunkRepository.findById(chunkId).isPresent()
        ? Optional.of(getTileAt(directionLocation))
        : Optional.empty();
  }

  public Map<Direction, TileType> getTileNeighbors(Location location) {
    Map<Direction, TileType> neighbors = Maps.newEnumMap(Direction.class);
    putTileNeighbor(location, NORTH, neighbors);
    putTileNeighbor(location, EAST, neighbors);
    putTileNeighbor(location, SOUTH, neighbors);
    putTileNeighbor(location, WEST, neighbors);
    return neighbors;
  }

  private void putTileNeighbor(Location location, Direction direction, Map<Direction, TileType> neighbors) {
    neighbors.put(direction, getTileAt(location.from(direction)));
  }

  public TileType getTileAt(Location location) {
    return getOrCreateChunkAt(location).getTileAt(location);
  }

}
