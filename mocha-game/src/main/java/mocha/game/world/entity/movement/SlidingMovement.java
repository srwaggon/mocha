package mocha.game.world.entity.movement;

import java.util.Optional;
import java.util.Set;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.collision.Collider;
import mocha.game.world.collision.Collision;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class SlidingMovement extends BaseMovement {

  private static final int WALK_SPEED = 2;

  private Repository<Entity, Integer> entityRepository;

  SlidingMovement(int id, Repository<Entity, Integer> entityRepository) {
    super(id);
    this.entityRepository = entityRepository;
  }

  @Override
  public void up() {
    this.yOffset = Direction.NORTH.getYMultiplier();
  }

  @Override
  public void down() {
    this.yOffset = Direction.SOUTH.getYMultiplier();
  }

  @Override
  public void left() {
    this.xOffset = Direction.WEST.getXMultiplier();
  }

  @Override
  public void right() {
    this.xOffset = Direction.EAST.getXMultiplier();
  }

  @Override
  public void move(Entity entity) {
    if (isMoving()) {
      int xDelta = xOffset * WALK_SPEED;
      int yDelta = yOffset * WALK_SPEED;

      move2(entity, xDelta, 0);
      move2(entity, 0, yDelta);
    }
  }

  private void move2(Entity entity, int xDelta, int yDelta) {
    Location next = entity.getLocation().addNew(xDelta, yDelta);
    Collision collision = entity.getCollision();

    Set<Collider> colliders = collision.getColliders(next);

    colliders.forEach(collider -> collider.collide(entity));

    if (colliders.stream().anyMatch(Collider::isBlocking)) {
      return;
    }

    entity.getLocation().set(next);
    entityRepository.save(entity);
  }

  private Optional<Entity> getEntity() {
    return entityRepository.findById(this.getId());
  }

  @Override
  public Location getLocation() {
    return getEntity()
        .map(Entity::getLocation)
        .orElse(null);
  }

}
