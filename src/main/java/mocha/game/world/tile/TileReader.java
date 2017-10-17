package mocha.game.world.tile;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class TileReader {

  private Map<String, TileType> typeByDescription = Maps.newHashMap();

  public TileReader() {
    Arrays.stream(TileType.values())
        .forEach(tileType ->
            typeByDescription.put(tileType.getSymbol(), tileType));
  }

  public Tile read(String tileDescription) {
    return Tile.builder()
        .tileType(typeByDescription.get(tileDescription))
        .build();
  }
}
