package mocha.game.world.entity.movement.collision;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class EntityCollision extends SimpleCollision {

  private Repository<Chunk, Integer> chunkRepository;
  private Entity entity;
  private int width;
  private int height;

  EntityCollision(Repository<Chunk, Integer> chunkRepository, Entity entity, int width, int height) {
    this.chunkRepository = chunkRepository;
    this.entity = entity;
    this.width = width;
    this.height = height;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return chunkRepository.findById(Chunk.getIdForChunkAt(location))
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
