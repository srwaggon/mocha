package mocha.game.world.collision;

import com.google.common.collect.Sets;

import java.util.Set;

import mocha.game.world.Location;

public class HitBoxCollision extends SimpleCollision {

  private int width;
  private int height;
  private Collision collision;

  HitBoxCollision(Collision collision, int width, int height) {
    this.width = width;
    this.height = height;
    this.collision = collision;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    Set<Collider> colliders = Sets.newHashSet();

    colliders.addAll(collision.getColliders(topLeft(location)));
    colliders.addAll(collision.getColliders(topRight(location)));
    colliders.addAll(collision.getColliders(bottomLeft(location)));
    colliders.addAll(collision.getColliders(bottomRight(location)));

    return colliders;
  }

  private Location topLeft(Location location) {
    int x = location.getX();
    int y = location.getY();
    return new Location(x, y);
  }

  private Location topRight(Location location) {
    int x = location.getX() + width - 1;
    int y = location.getY();
    return new Location(x, y);
  }

  private Location bottomLeft(Location location) {
    int x = location.getX();
    int y = location.getY() + height - 1;
    return new Location(x, y);
  }

  private Location bottomRight(Location location) {
    int x = location.getX() + width - 1;
    int y = location.getY() + height - 1;
    return new Location(x, y);
  }
}
