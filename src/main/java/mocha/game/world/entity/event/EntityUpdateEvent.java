package mocha.game.world.entity.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.world.entity.Entity;

@Data
@AllArgsConstructor
public class EntityUpdateEvent {
  private final Entity entity;
}
