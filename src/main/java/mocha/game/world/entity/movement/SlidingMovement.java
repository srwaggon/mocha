package mocha.game.world.entity.movement;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Queue;

import lombok.Builder;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;

public class SlidingMovement extends SimpleMovement {

  private double distance;
  private double duration;
  private Direction direction;

  @Builder
  private SlidingMovement(Location location, Collision collision, double distance, double duration, Direction direction, Queue<Runnable> turns) {
    super(location, collision);
    this.duration = duration;
    this.direction = direction;
    this.distance = distance / duration;
    this.turns = turns;
    Preconditions.checkArgument(duration > 0, "Duration must be greater than 0.");
  }

  private Queue<Runnable> turns = Lists.newLinkedList();

  @Override
  public void tick(long now) {
    if (!turns.isEmpty()) {
      turns.poll().run();
    }
  }

  @Override
  public void up() {
    if (turns.isEmpty()) {
      direction = Direction.NORTH;
      addMoves();
    }
  }

  @Override
  public void down() {
    if (turns.isEmpty()) {
      direction = Direction.SOUTH;
      addMoves();
    }
  }

  @Override
  public void left() {
    if (turns.isEmpty()) {
      direction = Direction.WEST;
      addMoves();
    }
  }

  @Override
  public void right() {
    if (turns.isEmpty()) {
      direction = Direction.EAST;
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

  public static class SlidingMovementBuilder extends SimpleMovementBuilder {
    public SlidingMovementBuilder() {
      super();
    }
  }

}
