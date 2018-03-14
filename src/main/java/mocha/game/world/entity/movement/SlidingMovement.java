package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.tile.TileType;

public class SlidingMovement extends BaseMovement {

  private static final int WALK_SPEED = 1;
  private static final int RUN_SPEED = 2;


  private boolean isMoving = false;
  private Direction direction;
  private int xOffset = 0;
  private int yOffset = 0;

  SlidingMovement(Collision collision) {
    super(collision);
  }

  @Override
  public void up() {
    if (isMoving) {
      return;
    }
    this.direction = Direction.NORTH;
    this.yOffset = direction.getYMultiplier() * TileType.SIZE;
    isMoving = true;
  }

  @Override
  public void down() {
    if (isMoving) {
      return;
    }
    this.direction = Direction.SOUTH;
    this.yOffset = direction.getYMultiplier() * TileType.SIZE;
  }

  @Override
  public void left() {
    if (isMoving) {
      return;
    }
    this.direction = Direction.WEST;
    this.xOffset = direction.getXMultiplier() * TileType.SIZE;
  }

  @Override
  public void right() {
    if (isMoving) {
      return;
    }
    this.direction = Direction.EAST;
    this.xOffset = direction.getXMultiplier() * TileType.SIZE;
  }

  @Override
  public void tick(long now) {
    isMoving = xOffset != 0 || yOffset != 0;
    if (isMoving) {
      updateLocation();
    }
  }

  private void updateLocation() {
    int xDelta = WALK_SPEED * direction.getXMultiplier();
    int yDelta = WALK_SPEED * direction.getYMultiplier();
    xOffset -= xDelta;
    yOffset -= yDelta;
    getLocation().add(xDelta, yDelta);
  }
}
