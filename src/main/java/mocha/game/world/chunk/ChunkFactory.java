package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileType;

@Builder
@Component
@AllArgsConstructor
public class ChunkFactory {

  private static int mapId = 0;

  @Inject
  private ChunkReader chunkReader;

  public Chunk newGrid() {
    return Chunk.builder()
        .tiles(createGridTiles(16, 16))
        .build();
  }

  public Chunk newRandomDefault() {
    return newRandomDefault(16, 16);
  }

  public Chunk newRandomDefault(int columns, int rows) {
    return Chunk.builder()
        .tiles(createRandomTiles(columns, rows))
        .build();
  }

  private Tile[][] createGridTiles(int columns, int rows) {
    Tile[][] tiles = new Tile[rows][columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y][x] = Tile.builder().tileType(TileType.GRID).build();
      }
    }
    return tiles;
  }

  private Tile[][] createRandomTiles(int columns, int rows) {
    Tile[][] tiles = new Tile[rows][columns];

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y][x] = Tile.builder()
            .tileType(TileType.random())
            .build();
      }
    }
    return tiles;
  }
}
