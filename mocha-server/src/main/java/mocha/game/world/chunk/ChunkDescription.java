package mocha.game.world.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ChunkDescription {
  private String tiles;
}
