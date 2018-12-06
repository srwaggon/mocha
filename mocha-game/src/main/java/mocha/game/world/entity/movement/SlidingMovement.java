package mocha.game.world.entity.movement;

import java.util.Set;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.Collider;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.shared.Repository;

public class SlidingMovement extends BaseMovement {

  private static final int WALK_SPEED = 2;

  private Repository<Entity, Integer> entityRepository;

  SlidingMovement(int id, Collision collision, Repository<Entity, Integer> entityRepository) {
    super(id, collision);
    this.entityRepository = entityRepository;
  }

  @Override
  public void up() {
    this.direction = Direction.NORTH;
    this.yOffset = direction.getYMultiplier();
  }

  @Override
  public void down() {
    this.direction = Direction.SOUTH;
    this.yOffset = direction.getYMultiplier();
  }

  @Override
  public void left() {
    this.direction = Direction.WEST;
    this.xOffset = direction.getXMultiplier();
  }

  @Override
  public void right() {
    this.direction = Direction.EAST;
    this.xOffset = direction.getXMultiplier();
  }

  @Override
  public void tick(long now) {
    if (isMoving()) {
      move();
    }
  }

  private void move() {
    int xDelta = xOffset * WALK_SPEED;
    int yDelta = yOffset * WALK_SPEED;

    move2(xDelta, 0);
    move2(0, yDelta);
  }

  private void move2(int xDelta, int yDelta) {
    Location next = getLocation().addNew(xDelta, yDelta);

    Set<Collider> colliders = collision.getColliders(next);

    informColliders(colliders);

    if (colliders.stream().anyMatch(Collider::isBlocking)) {
      return;
    }

    getLocation().set(next);
  }

  private void informColliders(Set<Collider> colliders) {
    entityRepository.findById(this.getId())
        .ifPresent(entity -> colliders
            .forEach(collider -> collider.collide(entity)));
  }

  @Override
  public Location getLocation() {
    return entityRepository.findById(this.getId())
        .map(Entity::getLocation)
        .orElse(null);
  }

}
