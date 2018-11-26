package mocha.game.world.chunk;

import mocha.game.world.Location;
import mocha.game.world.tile.TileSetFactory;

public class ChunkFactory {

  private TileSetFactory tileSetFactory;

  public ChunkFactory(TileSetFactory tileSetFactory) {
    this.tileSetFactory = tileSetFactory;
  }

  Chunk newRandomChunk(int chunkId) {
    Location location = ChunkIdHelper.getLocationOfChunkById(chunkId);
    return new Chunk(chunkId, location, tileSetFactory.createRandomTiles());
  }
}
