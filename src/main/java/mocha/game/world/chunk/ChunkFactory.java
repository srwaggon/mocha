package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileFactory;
import mocha.game.world.tile.TileType;
import mocha.game.world.tile.item.TileItem;
import mocha.game.world.tile.item.TileItemFactory;

@Builder
@Component
@AllArgsConstructor
public class ChunkFactory {

  private static int mapId = 0;

  @Inject
  private TileFactory tileFactory;

  @Inject
  private TileItemFactory tileItemFactory;

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
        TileType randomTileType = x == y ? TileType.DIRT : TileType.random();
        TileItem tileItem = canPlaceStone(randomTileType) && (int) (Math.random() * 8) == 0 ? tileItemFactory.newStone() : null;
        tiles[y][x] = Tile.builder()
            .tileType(randomTileType)
            .tileItem(tileItem)
            .build();
      }
    }
    return tiles;
  }

  private boolean canPlaceStone(TileType randomTileType) {
    return randomTileType == TileType.DIRT || randomTileType == TileType.GRASS;
  }

  public Chunk read(ChunkDescription chunkDescription) {
    int rows = Chunk.SIZE;
    int columns = Chunk.SIZE;
    Tile[][] tiles = new Tile[rows][columns];

    for (int i = 0; i < chunkDescription.getTiles().length(); i++) {
      int x = i % columns;
      int y = i / columns;
      tiles[y][x] = tileFactory.newTile("" + chunkDescription.getTiles().charAt(i));
    }

    return Chunk.builder().tiles(tiles).build();
  }
}
