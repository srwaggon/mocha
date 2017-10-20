package mocha.game.world.tile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.tile.item.TileItem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tile {

  public static final int SIZE = 32;
  private TileType tileType;
  private TileItem tileItem;
}
