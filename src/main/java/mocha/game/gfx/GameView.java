package mocha.game.gfx;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.Game;
import mocha.game.world.gfx.MapView;

public class GameView {
  MapView mapView;

  public GameView(Game game) {
    mapView = new MapView(game.getWorld().getMapById(1));
  }

  public void paint(GraphicsContext graphics) {
    mapView.paint(graphics);
  }
}
