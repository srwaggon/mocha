package mocha.game.world.entity.brain;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;

@Builder
@AllArgsConstructor
public class RandomBrain extends SimpleBrain {

  private static Map<Direction, Consumer<Movement>> directionMap = buildDirectionMap();

  private Entity entity;

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
