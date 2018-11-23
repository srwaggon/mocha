package mocha.game.world.entity.movement.collision;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class CollisionFactory {

  private Repository<Chunk, Integer> chunkRepository;

  public CollisionFactory(Repository<Chunk, Integer> chunkRepository) {
    this.chunkRepository = chunkRepository;
  }

  public SimpleCollision newSimpleCollision() {
    return new SimpleCollision();
  }

  private TileCollision newTileCollision() {
    return new TileCollision(chunkRepository);
  }

  public HitBoxCollision newTileHitBoxCollision(int width, int height) {
    return new HitBoxCollision(newTileCollision(), width, height);
  }

  private EntityCollision newEntityCollision(Entity entity, int width, int height) {
    return new EntityCollision(chunkRepository, entity, width, height);
  }

  public HitBoxCollision newEntityHitBoxCollision(Entity entity, int width, int height) {
    return new HitBoxCollision(newEntityTileCollision(entity, width, height), width, height);
  }

  private UnionCollision newEntityTileCollision(Entity entity, int width, int height) {
    return new UnionCollision(newEntityCollision(entity, width, height), newTileCollision());
  }
}
