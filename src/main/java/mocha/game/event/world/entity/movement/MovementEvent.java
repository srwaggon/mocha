package mocha.game.event.world.entity.movement;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.Movement;

@Data
@AllArgsConstructor
public class MovementEvent {
  private final Movement movement;
  private final Location location;
}
