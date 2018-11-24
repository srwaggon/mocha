package mocha.game.world.entity.movement.collision;

import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;

public class CollisionFactory {

  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;

  public CollisionFactory(
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService
  ) {
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
  }

  public SimpleCollision newSimpleCollision() {
    return new SimpleCollision();
  }

  private TileCollision newTileCollision() {
    return new TileCollision(chunkService);
  }

  public HitBoxCollision newTileHitBoxCollision(int width, int height) {
    return new HitBoxCollision(newTileCollision(), width, height);
  }

  private EntityCollision newEntityCollision(Entity entity, int width, int height) {
    return new EntityCollision(chunkService, entitiesInChunkService, entity, width, height);
  }

  public HitBoxCollision newEntityHitBoxCollision(Entity entity, int width, int height) {
    return new HitBoxCollision(newEntityTileCollision(entity, width, height), width, height);
  }

  private UnionCollision newEntityTileCollision(Entity entity, int width, int height) {
    return new UnionCollision(newEntityCollision(entity, width, height), newTileCollision());
  }
}
