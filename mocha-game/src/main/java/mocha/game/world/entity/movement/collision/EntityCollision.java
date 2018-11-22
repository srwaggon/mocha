package mocha.game.world.entity.movement.collision;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

public class EntityCollision extends SimpleCollision {

  private World world;
  private Entity entity;
  private int width;
  private int height;

  EntityCollision(World world, Entity entity, int width, int height) {
    this.world = world;
    this.entity = entity;
    this.width = width;
    this.height = height;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return world
        .getChunkAt(entity.getLocation())
        .map(chunk -> getColliders(location, chunk))
        .orElse(Collections.emptySet());
  }

  private Set<Collider> getColliders(Location location, Chunk chunk) {
    return chunk.getEntitiesAt(entity.getLocation()).stream()
        .filter(this::isNotSelf)
        .filter(entity -> new RectangularCollision(entity.getLocation(), width, height).isColliding(location))
        .collect(Collectors.toSet());
  }

  private boolean isNotSelf(Entity entity) {
    return entity.getId() != this.entity.getId();
  }
}
