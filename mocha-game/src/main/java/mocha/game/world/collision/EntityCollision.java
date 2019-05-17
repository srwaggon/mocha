package mocha.game.world.collision;

import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;

public class EntityCollision extends SimpleCollision {

  private ChunkService chunkService;
  private Entity entity;
  private int width;
  private int height;
  private EntityService entityService;

  EntityCollision(
      ChunkService chunkService,
      Entity entity,
      int width,
      int height,
      EntityService entityService
  ) {
    this.chunkService = chunkService;
    this.entity = entity;
    this.width = width;
    this.height = height;
    this.entityService = entityService;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return getColliders(location, chunkService.getOrCreateChunkAt(location));
  }

  private Set<Collider> getColliders(Location location, Chunk chunk) {
    return entityService.getEntitiesInChunk(chunk).stream()
        .filter(this::isNotSelf)
        .filter(entity -> new RectangularCollision(entity.getLocation(), width, height).isColliding(location))
        .collect(Collectors.toSet());
  }

  private boolean isNotSelf(Entity entity) {
    return !entity.getId().equals(this.entity.getId());
  }
}
