package mocha.game.world.entity.movement.collision;

import java.util.Set;
import java.util.stream.Collectors;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;

public class EntityCollision extends SimpleCollision {

  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;
  private Entity entity;
  private int width;
  private int height;

  EntityCollision(
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService,
      Entity entity,
      int width,
      int height
  ) {
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
    this.entity = entity;
    this.width = width;
    this.height = height;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return getColliders(location, chunkService.getChunkAt(location));
  }

  private Set<Collider> getColliders(Location location, Chunk chunk) {
    return entitiesInChunkService.getEntitiesInChunk(chunk).stream()
        .filter(this::isNotSelf)
        .filter(entity -> new RectangularCollision(entity.getLocation(), width, height).isColliding(location))
        .collect(Collectors.toSet());
  }

  private boolean isNotSelf(Entity entity) {
    return entity.getId() != this.entity.getId();
  }
}
