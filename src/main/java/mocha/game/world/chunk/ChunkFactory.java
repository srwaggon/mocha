package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.tile.TileFactory;
import mocha.game.world.tile.TileType;

@Builder
@Component
@AllArgsConstructor
public class ChunkFactory {

  private static int mapId = 0;

  @Inject
  private TileFactory tileFactory;

  public Chunk newGrid() {
    return Chunk.builder()
        .tiles(createGridTiles(16, 16))
        .build();
  }

  public Chunk newRandomDefault(int chunkX, int chunkY) {
    return Chunk.builder()
        .tiles(createRandomTiles(chunkX, chunkY))
        .build();
  }

  private TileType[][] createGridTiles(int columns, int rows) {
    TileType[][] tiles = new TileType[rows][columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y][x] = TileType.GRID;
      }
    }
    return tiles;
  }

  private TileType[][] createRandomTiles(int chunkX, int chunkY) {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    TileType[][] tiles = new TileType[rows][columns];

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y][x] = x == y ? TileType.DIRT : TileType.random();
      }
    }
    return tiles;
  }

  public Chunk read(ChunkDescription chunkDescription) {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    TileType[][] tiles = new TileType[rows][columns];

    for (int i = 0; i < chunkDescription.getTiles().length(); i++) {
      int x = i % columns;
      int y = i / columns;
      tiles[y][x] = tileFactory.newTile("" + chunkDescription.getTiles().charAt(i));
    }

    return Chunk.builder().tiles(tiles).build();
  }
}
