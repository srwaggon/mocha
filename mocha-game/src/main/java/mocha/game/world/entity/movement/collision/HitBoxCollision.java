package mocha.game.world.entity.movement.collision;

import mocha.game.world.Location;

public class HitBoxCollision extends SimpleCollision {

  private int width;
  private int height;
  private Collision collision;

  public HitBoxCollision(Collision collision, int width, int height) {
    this.width = width;
    this.height = height;
    this.collision = collision;
  }

  @Override
  public boolean collides(Location location) {
    boolean tl = collision.collides(topLeft(location));
    boolean tr = collision.collides(topRight(location));
    boolean bl = collision.collides(bottomLeft(location));
    boolean br = collision.collides(bottomRight(location));
    return tl || tr || bl || br;
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
