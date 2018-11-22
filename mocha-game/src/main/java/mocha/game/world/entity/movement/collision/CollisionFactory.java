package mocha.game.world.entity.movement.collision;

import mocha.game.world.World;
import mocha.game.world.entity.Entity;

public class CollisionFactory {

  private World world;

  public CollisionFactory(World world) {
    this.world = world;
  }

  public SimpleCollision newSimpleCollision() {
    return new SimpleCollision();
  }

  private TileCollision newTileCollision() {
    return new TileCollision(world);
  }

  public HitBoxCollision newTileHitBoxCollision(int width, int height) {
    return new HitBoxCollision(newTileCollision(), width, height);
  }

  private EntityCollision newEntityCollision(Entity entity, int width, int height) {
    return new EntityCollision(world, entity, width, height);
  }

  public HitBoxCollision newEntityHitBoxCollision(Entity entity, int width, int height) {
    return new HitBoxCollision(newEntityTileCollision(entity, width, height), width, height);
  }

  private UnionCollision newEntityTileCollision(Entity entity, int width, int height) {
    return new UnionCollision(newEntityCollision(entity, width, height), newTileCollision());
  }
}
