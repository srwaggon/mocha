package mocha.game.world.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mocha.game.world.entity.brain.Brain;
import mocha.game.world.entity.brain.SimpleBrain;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;

@Data
@Builder
@AllArgsConstructor
public class Entity {
  private Movement movement = new SimpleMovement();
  private Brain brain = new SimpleBrain();

  public int getSpriteId() {
    return 130;
  }

  public double getScale() {
    return 2.0;
  }

  public String toString() {
    return "{\"location\": " + this.getMovement().getLocation() + "}";
  }
}
