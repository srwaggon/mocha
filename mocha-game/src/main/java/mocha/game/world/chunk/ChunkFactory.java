package mocha.game.world.chunk;

import mocha.game.world.tile.TileSetFactory;

public class ChunkFactory {

  private TileSetFactory tileSetFactory;

  public ChunkFactory(TileSetFactory tileSetFactory) {
    this.tileSetFactory = tileSetFactory;
  }

  public Chunk newRandomChunk(int chunkId) {
    return new Chunk(chunkId, tileSetFactory.createRandomTiles());
  }
}
