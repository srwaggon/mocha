package mocha.game.world.entity.brain;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Consumer;

import mocha.game.world.Direction;
import mocha.game.world.entity.movement.Movement;

public class RandomBrain extends BaseBrain {

  private static Map<Direction, Consumer<Movement>> directionMap = buildDirectionMap();

  private static Map<Direction, Consumer<Movement>> buildDirectionMap() {
    Map<Direction, Consumer<Movement>> directionMap = Maps.newConcurrentMap();
    directionMap.put(Direction.NORTH, Movement::up);
    directionMap.put(Direction.EAST, Movement::right);
    directionMap.put(Direction.SOUTH, Movement::down);
    directionMap.put(Direction.WEST, Movement::left);
    return directionMap;
  }

  @Override
  public void tick(long now) {
    directionMap.get(Direction.random()).accept(entity.getMovement());
  }

}
