package mocha.game.world.entity.movement.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.world.entity.movement.Movement;

@Data
@AllArgsConstructor
public class EntityMovementEvent {
  private final Movement movement;
}
