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
    applyXDelta();
    applyYDelta();
  }

  private void applyXDelta() {
    double xDelta = distance * this.direction.getXMultiplier();
    Location nextX = this.getLocation().add(xDelta, 0);
    if (!collision.collides(nextX)) {
      this.getLocation().addX(xDelta);
    }
  }

  private void applyYDelta() {
    double yDelta = distance * this.direction.getYMultiplier();
    Location nextY = this.getLocation().add(0, yDelta);
    if (!collision.collides(nextY)) {
      this.getLocation().addY(yDelta);
    }
  }

  public static class SlidingMovementBuilder extends SimpleMovementBuilder {
    public SlidingMovementBuilder() {
      super();
    }
  }

}
