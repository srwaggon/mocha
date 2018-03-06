package mocha.game.world.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.entity.brain.Brain;
import mocha.game.world.entity.movement.Movement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entity {
  private int id;
  private Movement movement;
  private Brain brain;

  public int getSpriteId() {
    return 130;
  }

  public double getScale() {
    return 2.0;
  }

  public String toString() {
    return "{" +
        "\"id\":" + this.getId() + ", " +
        "\"location\": " + this.getMovement().getLocation() +
        "}";
  }
}
