package mocha.game.world.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEntityEvent {
  private final Entity entity;
}
