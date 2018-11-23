package mocha.game.world.entity.movement;

import com.google.common.collect.Maps;

import java.util.Map;

import mocha.game.world.Direction;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class BaseMovement extends SimpleMovement {

  private final Map<Direction, Runnable> moveMap = Maps.newConcurrentMap();
  private int id;

  BaseMovement(Collision collision, int id) {
    super(collision);
    this.id = id;

    moveMap.put(Direction.NORTH, this::up);
    moveMap.put(Direction.EAST, this::right);
    moveMap.put(Direction.SOUTH, this::down);
    moveMap.put(Direction.WEST, this::left);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public void handle(EntityMoveCommand moveCommand) {
    moveMap.get(moveCommand.getDirection()).run();
  }

}

