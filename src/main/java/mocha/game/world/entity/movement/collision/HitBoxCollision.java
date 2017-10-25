package mocha.game.world.entity.movement.collision;

import lombok.Builder;
import lombok.Data;
import mocha.game.world.Location;

@Data
@Builder
public class HitBoxCollision extends SimpleCollision {

  private int width;
  private int height;
  private TileCollision tileCollision;

  @Override
  public boolean collides(Location location) {
    boolean tl = tileCollision.collides(topLeft(location));
    boolean tr = tileCollision.collides(topRight(location));
    boolean bl = tileCollision.collides(bottomLeft(location));
    boolean br = tileCollision.collides(bottomRight(location));
    return tl || tr || bl || br;
  }

  private Location topLeft(Location location) {
    double y = location.getY();
    double x = location.getX();
    return new Location(x, y);
  }

  private Location topRight(Location location) {
    double x = location.getX() + this.getWidth() - 1;
    double y = location.getY();
    return new Location(x, y);
  }

  private Location bottomLeft(Location location) {
    double x = location.getX();
    double y = location.getY() + this.getHeight() - 1;
    return new Location(x, y);
  }

  private Location bottomRight(Location location) {
    double x = location.getX() + this.getWidth() - 1;
    double y = location.getY() + this.getHeight() - 1;
    return new Location(x, y);
  }
}
