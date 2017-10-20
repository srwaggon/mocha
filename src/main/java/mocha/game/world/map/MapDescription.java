package mocha.game.world.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapDescription {
  private int id;
  private int columns;
  private int rows;
  private String tiles;
}
