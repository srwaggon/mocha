package mocha.game.event.world.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.world.entity.Entity;

@Data
@AllArgsConstructor
public class AddEntityEvent {
  private final Entity entity;
}
