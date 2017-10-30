package mocha.game.world.entity.movement;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.tile.TileType;

@Component
public class MovementFactory {

  @Inject
  private CollisionFactory collisionFactory;

  @Inject
  private EventBus eventBus;

  public VelocityMovement newVelocityMovement() {
    return VelocityMovement.builder()
        .location(new Location())
        .collision(collisionFactory.newHitBoxCollision())
        .eventBus(eventBus)
        .speed(2)
        .build();
  }

  public SimpleMovement newSimpleMovement() {
    return SimpleMovement.builder()
        .location(new Location())
        .collision(collisionFactory.newSimpleCollision())
        .build();
  }

  public SlidingMovement newSlidingMovement() {
    return SlidingMovement.builder()
        .location(new Location())
        .collision(collisionFactory.newHitBoxCollision())
        .eventBus(eventBus)
        .distance(TileType.SIZE)
        .duration(30)
        .direction(Direction.EAST)
        .turns(Lists.newLinkedList())
        .build();
  }

  public AccelerationMovement newAccelerationMovement() {
    return AccelerationMovement.builder()
        .location(new Location())
        .collision(collisionFactory.newHitBoxCollision())
        .eventBus(eventBus)
        .accelerationRate(.1)
        .maxXVelocity(2.0)
        .maxYVelocity(2.0)
        .build();
  }
}