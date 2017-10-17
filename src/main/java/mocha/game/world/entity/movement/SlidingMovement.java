package mocha.game.world.entity.movement;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Queue;

import mocha.game.InputKey;
import mocha.game.world.Direction;

public class SlidingMovement extends SimpleMovement {

  private double distance;
  private double duration;
  private Direction direction;

  public SlidingMovement(double distance, int duration) {
    this.duration = duration;
    this.distance = distance / duration;
    Preconditions.checkArgument(duration > 0, "Duration must be greater than 0.");
  }

  private Queue<Runnable> turns = Lists.newLinkedList();

  @Override
  public void tick() {
    startMoving();

    if (!turns.isEmpty()) {
      turns.poll().run();
    }
  }

  private void startMoving() {
    if (!turns.isEmpty()) {
      return;
    }

    if (InputKey.RIGHT.isDown()) {
      direction = Direction.EAST;
      addMoves();
    }

    if (InputKey.DOWN.isDown()) {
      direction = Direction.SOUTH;
      addMoves();
    }

    if (InputKey.UP.isDown()) {
      direction = Direction.NORTH;
      addMoves();
    }

    if (InputKey.LEFT.isDown()) {
      direction = Direction.WEST;
      addMoves();
    }
  }

  private void addMoves() {
    for (int i = 0; i < duration; i++) {
      turns.add(this::move);
    }
  }

  private void move() {
    this.getLocation().addX(distance * this.direction.getXMultiplier());
    this.getLocation().addY(distance * this.direction.getYMultiplier());
  }

}
