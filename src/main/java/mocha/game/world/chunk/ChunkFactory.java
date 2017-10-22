package mocha.game.world.chunk;

import com.google.common.collect.Sets;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileType;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ChunkFactory {

  private static int mapId = 0;

  @Inject
  private ChunkReader chunkReader;

  public Chunk newGrid() {
    return Chunk.builder()
      .id(mapId++)
      .tiles(createGridTiles(18, 12))
      .entities(Sets.newIdentityHashSet())
      .build();
  }

  public Chunk newRandomDefault() {
    return newRandomDefault(18, 12);
  }

  public Chunk newRandomDefault(int columns, int rows) {
    return Chunk.builder()
      .tiles(createRandomTiles(columns, rows))
      .entities(Sets.newIdentityHashSet())
      .id(mapId++)
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
