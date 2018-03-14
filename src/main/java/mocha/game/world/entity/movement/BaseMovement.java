package mocha.game.world.entity.movement;

import com.google.common.collect.Maps;

import java.util.Map;

import mocha.game.world.Direction;
import mocha.game.world.entity.movement.collision.Collision;

public class BaseMovement extends SimpleMovement {

  private final Map<Direction, Runnable> moveMap = Maps.newConcurrentMap();

  BaseMovement(Collision collision) {
    super(collision);

    moveMap.put(Direction.NORTH, this::up);
    moveMap.put(Direction.EAST, this::right);
    moveMap.put(Direction.SOUTH, this::down);
    moveMap.put(Direction.WEST, this::left);
  }

  public static BaseMovementBuilder builder() {
    return new BaseMovementBuilder();
  }

  @Override
  public void handle(Move move) {
    moveMap.get(move.getDirection()).run();
  }

  static class BaseMovementBuilder extends SimpleMovement.SimpleMovementBuilder {
    public BaseMovement build() {
      return new BaseMovement(collision);
    }
  }

}

