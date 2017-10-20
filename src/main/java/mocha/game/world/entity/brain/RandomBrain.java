package mocha.game.world.entity.brain;

import com.google.common.collect.Maps;

import java.util.Map;

import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;

public class RandomBrain extends SimpleBrain {

  private Entity entity;

  public RandomBrain(Entity entity) {
    this.entity = entity;
  }

  private Map<Direction, Runnable> buildDirectionMap() {
    Movement movement = entity.getMovement();
    Map<Direction, Runnable> directionMap = Maps.newConcurrentMap();
    directionMap.put(Direction.NORTH, movement::up);
    directionMap.put(Direction.EAST, movement::right);
    directionMap.put(Direction.SOUTH, movement::down);
    directionMap.put(Direction.WEST, movement::left);
    return directionMap;
  }

  @Override
  public void tick(long now) {
    Map<Direction, Runnable> directionMap = buildDirectionMap();
    directionMap.get(Direction.random()).run();
  }

}
