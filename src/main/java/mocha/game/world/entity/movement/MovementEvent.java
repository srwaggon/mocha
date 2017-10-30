package mocha.game.world.entity.movement;

import lombok.Data;
import mocha.game.world.Location;

@Data
public class MovementEvent {

  private final Movement movement;
  private final Location location;

  public MovementEvent(Movement movement, Location location) {
    this.movement = movement;
    this.location = location;
  }
}
