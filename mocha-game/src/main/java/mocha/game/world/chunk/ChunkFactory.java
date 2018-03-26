package mocha.game.world.chunk;

import com.google.common.collect.Sets;

import lombok.Builder;
import mocha.game.world.tile.TileFactory;
import mocha.game.world.tile.TileType;

@Builder
public class ChunkFactory {

  private static int mapId = 0;

  private TileFactory tileFactory;

  public ChunkFactory(TileFactory tileFactory) {
    this.tileFactory = tileFactory;
  }

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
      tiles[y * rows + x] = tileFactory.newTile("" + chunkDescription.getTiles().charAt(i));
    }

    return Chunk.builder()
        .tiles(tiles)
        .entities(Sets.newHashSet())
        .build();
  }
}
