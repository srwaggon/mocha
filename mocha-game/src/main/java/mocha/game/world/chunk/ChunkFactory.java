package mocha.game.world.chunk;

import com.google.common.collect.Sets;

import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileType;

public class ChunkFactory {

  private TileSetFactory tileSetFactory;

  public ChunkFactory(TileSetFactory tileSetFactory) {
    this.tileSetFactory = tileSetFactory;
  }

  public Chunk newGrid() {
    return Chunk.builder()
        .tiles(createGridTiles())
        .entities(Sets.newHashSet())
        .build();
  }

  public Chunk newRandomDefault() {
    return Chunk.builder()
        .tiles(tileSetFactory.createRandomTiles())
        .entities(Sets.newHashSet())
        .build();
  }

  private TileType[] createGridTiles() {
    TileType[] tiles = new TileType[Chunk.SIZE * Chunk.SIZE];
    for (int y = 0; y < Chunk.SIZE; y++) {
      for (int x = 0; x < Chunk.SIZE; x++) {
        tiles[y * Chunk.SIZE + x] = TileType.GRID;
      }
    }
    return tiles;
  }

  public Chunk read(ChunkDescription chunkDescription) {
    return Chunk.builder()
        .tiles(tileSetFactory.newTilesFromDescription(chunkDescription))
        .entities(Sets.newHashSet())
        .build();
  }
}