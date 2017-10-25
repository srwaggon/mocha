package mocha.game.world.entity.movement;

import lombok.Builder;
import lombok.Data;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.collision.SimpleCollision;

@Data
public class SimpleMovement implements Movement {

  protected Location location = new Location();
  protected Collision collision = new SimpleCollision();

  @Builder
  public SimpleMovement(Location location, Collision collision) {
    this.location = location;
    this.collision = collision;
  }

  @Override
  public void up() {

  }

  @Override
  public void down() {

  }

  @Override
  public void left() {

  }

  @Override
  public void right() {

  }

  @Override
  public void tick(long now) {

  }
}
