package mocha.game.world.entity.movement;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;

import java.util.Queue;

import lombok.Builder;
import mocha.game.event.world.entity.movement.MovementEvent;
import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;

public class SlidingMovement extends SimpleMovement {

  private final EventBus eventBus;
  private double distance;
  private double duration;
  private Direction direction;
  private Queue<Runnable> turns;

  @Builder
  private SlidingMovement(Location location, Collision collision, double distance, double duration, Direction direction, Queue<Runnable> turns, EventBus eventBus) {
    super(location, collision);
    this.duration = duration;
    this.direction = direction;
    this.distance = distance / duration;
    this.turns = turns;
    this.eventBus = eventBus;
    Preconditions.checkArgument(duration > 0, "Duration must be greater than 0.");
  }

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
    Location nextLocation = applyYDelta(applyXDelta(getLocation()));
    eventBus.post(new MovementEvent(this, nextLocation));
  }

  private Location applyXDelta(Location location) {
    double xDelta = distance * this.direction.getXMultiplier();
    Location nextX = this.getLocation().add(xDelta, 0);
    return collision.collides(nextX) ? location : nextX;
  }

  private Location applyYDelta(Location location) {
    double yDelta = distance * this.direction.getYMultiplier();
    Location nextY = this.getLocation().add(0, yDelta);
    return collision.collides(nextY) ? location : nextY;
  }

  public static class SlidingMovementBuilder extends SimpleMovementBuilder {
    public SlidingMovementBuilder() {
      super();
    }
  }

}
