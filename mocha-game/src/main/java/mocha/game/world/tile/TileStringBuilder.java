package mocha.game.world.tile;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TileStringBuilder {

  public String build(TileType[] tiles) {
    return Arrays.stream(tiles)
        .map(TileType::getSymbol)
        .map(String::valueOf)
        .collect(Collectors.joining());
  }
}
