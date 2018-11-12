package mocha.game.world.chunk;

import com.google.common.collect.Sets;

import mocha.game.world.tile.TileType;

public class ChunkFactory {

  public Chunk newGrid() {
    return Chunk.builder()
        .tiles(createGridTiles(Chunk.SIZE, Chunk.SIZE))
        .entities(Sets.newHashSet())
        .build();
  }

  public Chunk newRandomDefault() {
    return Chunk.builder()
        .tiles(createRandomTiles())
        .entities(Sets.newHashSet())
        .build();
  }

  private TileType[] createGridTiles(int columns, int rows) {
    TileType[] tiles = new TileType[rows * columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y * rows + x] = TileType.GRID;
      }
    }
    return tiles;
  }

  private TileType[] createRandomTiles() {
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

  public Chunk read(ChunkDescription chunkDescription) {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    TileType[] tiles = new TileType[rows * columns];

    for (int i = 0; i < chunkDescription.getTiles().length(); i++) {
      int x = i % columns;
      int y = i / columns;
      char tileTypeSymbol = chunkDescription.getTiles().charAt(i);
      tiles[y * rows + x] = TileType.valueOf(tileTypeSymbol);
    }

    return Chunk.builder()
        .tiles(tiles)
        .entities(Sets.newHashSet())
        .build();
  }
}
