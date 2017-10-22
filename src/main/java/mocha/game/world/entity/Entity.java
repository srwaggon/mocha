package mocha.game.world.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mocha.game.world.entity.brain.Brain;
import mocha.game.world.entity.brain.SimpleBrain;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;

@Data
@EqualsAndHashCode
public class Entity {
  private static int lastId = 0;
  private final int id;
  private Movement movement = new SimpleMovement();
  private Brain brain = new SimpleBrain();
  private int chunkId;

  public Entity() {
    this.id = ++lastId;
  }

  public Entity(int id) {
    this.id = id;
    lastId = id > lastId ? id : lastId;
  }

  public int getSpriteId() {
    return 130;
  }

  public double getScale() {
    return 2.0;
  }

  public String toString() {
    return "{" +
        "\"id\": " + id + ", " +
        "\"chunk\": " + this.chunkId + ", " +
        "\"location\": " + this.getMovement().getLocation() +
        "}";
  }
}
