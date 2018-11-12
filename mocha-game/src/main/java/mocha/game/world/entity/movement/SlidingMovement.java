package mocha.game.world.entity.movement;

import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.Collider;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.tile.TileType;

public class SlidingMovement extends BaseMovement {

  private static final int WALK_SPEED = 1;
  private final Entity entity;

  SlidingMovement(Entity entity, Collision collision) {
    super(collision);
    this.entity = entity;
  }

  @Override
  public void up() {
    if (isMoving()) {
      return;
    }
    this.direction = Direction.NORTH;
    this.yOffset = direction.getYMultiplier() * TileType.SIZE;
  }

  @Override
  public void down() {
    if (isMoving()) {
      return;
    }
    this.direction = Direction.SOUTH;
    this.yOffset = direction.getYMultiplier() * TileType.SIZE;
  }

  @Override
  public void left() {
    if (isMoving()) {
      return;
    }
    this.direction = Direction.WEST;
    this.xOffset = direction.getXMultiplier() * TileType.SIZE;
  }

  @Override
  public void right() {
    if (isMoving()) {
      return;
    }
    this.direction = Direction.EAST;
    this.xOffset = direction.getXMultiplier() * TileType.SIZE;
  }

  @Override
  public void tick(long now) {
    if (isMoving()) {
      updateLocation();
    }
  }

  public boolean isMoving() {
    return xOffset != 0 || yOffset != 0;
  }

  private void updateLocation() {
    int xDelta = WALK_SPEED * direction.getXMultiplier();
    int yDelta = WALK_SPEED * direction.getYMultiplier();
    xOffset -= xDelta;
    yOffset -= yDelta;

    Location next = getLocation().addNew(xDelta, yDelta);

    Set<Collider> colliders = collision.getColliders(next);

    colliders.stream()
        .filter(collider -> !collider.isBlocking())
        .forEach(collider -> collider.collide(entity));

    if (colliders.stream().noneMatch(Collider::isBlocking)) {
      getLocation().set(next);
    }
  }
}
