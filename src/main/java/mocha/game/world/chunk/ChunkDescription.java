package mocha.game.world.chunk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkDescription {
  private String tiles;

  public ChunkDescription(String tiles) {
    this.tiles = tiles;
  }
}
