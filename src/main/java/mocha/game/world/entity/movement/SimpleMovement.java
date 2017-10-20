package mocha.game.world.entity.movement;

import lombok.Data;
import mocha.game.world.Location;

@Data
public class SimpleMovement implements Movement {

  protected Location location = new Location();

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
