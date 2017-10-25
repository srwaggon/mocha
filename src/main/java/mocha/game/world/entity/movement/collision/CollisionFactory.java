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

  public SimpleCollision newSimpleCollision() {
    return new SimpleCollision();
  }

  public TileCollision newTileCollision() {
    return TileCollision.builder()
        .world(world)
        .build();
  }

  public HitBoxCollision newHitBoxCollision() {
    return HitBoxCollision.builder()
        .width(24)
        .height(24)
        .tileCollision(newTileCollision())
        .build();
  }
}
