package mocha.client.gfx.view;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.geometry.Rectangle2D;
import mocha.game.player.PlayerService;
import mocha.game.world.Location;

@Component
public class Camera {

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
    return 512;
  }

  private int getHeight() {
    return 384;
  }

}
