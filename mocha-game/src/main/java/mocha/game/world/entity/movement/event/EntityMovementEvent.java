package mocha.game.world.entity.movement.event;

import mocha.game.event.MochaEvent;
import mocha.game.world.entity.movement.Movement;

public class EntityMovementEvent implements MochaEvent {
  private final Movement movement;

  public EntityMovementEvent(Movement movement) {
    this.movement = movement;
  }

  public Movement getMovement() {
    return movement;
  }
}
