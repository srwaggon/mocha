package mocha.game.world.entity.movement.collision;

public interface Collider {

  boolean isBlocking();

  void collide(Collider collider);
}
