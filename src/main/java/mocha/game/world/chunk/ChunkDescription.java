package mocha.game.world.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChunkDescription {
  private int id;
  private int columns;
  private int rows;
  private String tiles;
}
