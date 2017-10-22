package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileFactory;

@Builder
@Component
@AllArgsConstructor
public class ChunkReader {

  @Inject
  private TileFactory tileFactory;

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
