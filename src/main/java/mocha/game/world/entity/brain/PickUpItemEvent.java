package mocha.game.world.entity.brain;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.world.entity.Entity;

@Data
@AllArgsConstructor
public class PickUpItemEvent {
  private Entity entity;
}
