package mocha.game.world.entity.movement.collision;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.Location;
import mocha.game.world.World;

@Builder
@AllArgsConstructor
public class TileCollision extends SimpleCollision {

  private World world;

  @Override
  public boolean collides(Location location) {
    return false;
  }
}
