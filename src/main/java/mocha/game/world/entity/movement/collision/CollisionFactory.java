package mocha.game.world.entity.movement.collision;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.World;

@Builder
@AllArgsConstructor
@Component
public class CollisionFactory {

  private World world;

  public Collision newSimpleCollision() {
    return new SimpleCollision();
  }

  public Collision newTileCollision() {
    return TileCollision.builder()
        .world(world)
        .build();
  }
}
