package mocha.game.world.tile;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkDescription;

public class TileSetFactory {

  private TileReader tileReader;

  public TileSetFactory(TileReader tileReader) {
    this.tileReader = tileReader;
  }

  public TileType[] createRandomTiles() {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    TileType[] tiles = new TileType[rows * columns];

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y * rows + x] = x == y ? TileType.DIRT : TileType.random();
      }
    }
    return tiles;
  }

  public TileType[] newTilesFromDescription(ChunkDescription chunkDescription) {
    return tileReader.readTiles(chunkDescription.getTiles());
  }

}