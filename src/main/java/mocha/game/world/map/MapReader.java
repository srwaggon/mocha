package mocha.game.world.map;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileFactory;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class MapReader {

  @Inject
  private TileFactory tileFactory;

  public Map read(MapDescription mapDescription) {

    int rows = mapDescription.getRows();
    int columns = mapDescription.getColumns();
    Tile[][] tiles = new Tile[rows][columns];

    for (int i = 0; i < mapDescription.getTiles().length(); i++) {
      int x = i % columns;
      int y = i / columns;
      tiles[y][x] = tileFactory.newTile("" + mapDescription.getTiles().charAt(i));
    }

    return Map.builder()
        .id(mapDescription.getId())
        .tiles(tiles)
        .build();
  }
}
