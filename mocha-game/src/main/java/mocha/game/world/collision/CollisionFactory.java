package mocha.game.world.collision;

import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;

public class CollisionFactory {

  private ChunkService chunkService;
  private EntityService entityService;

  public CollisionFactory(
      ChunkService chunkService,
      EntityService entityService
  ) {
    this.chunkService = chunkService;
    this.entityService = entityService;
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
    return new EntityCollision(chunkService, entity, width, height, entityService);
  }

  public HitBoxCollision newEntityHitBoxCollision(Entity entity, int width, int height) {
    return new HitBoxCollision(newEntityTileCollision(entity, width, height), width, height);
  }

  private UnionCollision newEntityTileCollision(Entity entity, int width, int height) {
    return new UnionCollision(newEntityCollision(entity, width, height), newTileCollision());
  }

  public HitBoxCollision newEntityHitBoxCollision(Entity entity) {
    return newEntityHitBoxCollision(entity, entity.getWidth(), entity.getHeight());
  }
}
