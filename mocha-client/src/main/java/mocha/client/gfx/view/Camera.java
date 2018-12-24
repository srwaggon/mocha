package mocha.client.gfx.view;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.geometry.Rectangle2D;
import mocha.game.player.PlayerService;
import mocha.game.world.Location;

@Component
public class Camera {

  private Location location = new Location(0, 0);
  private PlayerService playerService;

  @Inject
  public Camera(PlayerService playerService) {
    this.playerService = playerService;
  }

  private Location getLocation() {
    return getPlayerLocation();
  }

  public Rectangle2D getBounds() {
    int width = getWidth();
    int height = getHeight();
    int x = getLocation().getX() - width / 2;
    int y = getLocation().getY() - height / 2;
    return new Rectangle2D(x, y, width, height);
  }

  private Location getPlayerLocation() {
    return playerService.getEntityForPlayer().getLocation();
  }

  private int getWidth() {
    return 1024;
  }

  private int getHeight() {
    return 768;
  }

  public Location bottomRight() {
    Rectangle2D bounds = getBounds();
    return new Location(bounds.getMaxX(), bounds.getMaxY());
  }

  public Location bottomLeft() {
    Rectangle2D bounds = getBounds();
    return new Location(bounds.getMinX(), bounds.getMaxY());
  }

  public Location topRight() {
    Rectangle2D bounds = getBounds();
    return new Location(bounds.getMaxX(), bounds.getMinY());
  }

  public Location topLeft() {
    Rectangle2D bounds = getBounds();
    return new Location(bounds.getMinX(), bounds.getMinY());
  }

}
