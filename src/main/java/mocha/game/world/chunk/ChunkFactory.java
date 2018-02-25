package mocha.game.world.chunk;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Builder;
import mocha.game.world.entity.Entity;
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
        .tileEntities(createTileEntities())
        .build();
  }

  public Chunk newRandomDefault(int chunkX, int chunkY) {
    return Chunk.builder()
        .tiles(createRandomTiles(chunkX, chunkY))
        .tileEntities(createTileEntities())
        .build();
  }

  private List<Entity>[] createTileEntities() {
    List<Entity>[] lists = (List<Entity>[]) new List[Chunk.SIZE * Chunk.SIZE];
    for (int i = 0; i < lists.length; i++) {
      lists[i] = Lists.newLinkedList();
    }
    return lists;
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

  private TileType[] createRandomTiles(int chunkX, int chunkY) {
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
        .tileEntities(createTileEntities())
        .build();
  }
}
