package mocha.game.world.collision;

public interface Collider {

  boolean isBlocking();

  void collide(Collider collider);
}
