package mocha.game.mocha.game.gfx;

import mocha.game.Game;
import mocha.game.world.gfx.MapView;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
  MapView mapView;

  public GameView(Game game) {
    mapView = new MapView(game.getWorld());
  }

  @Override public void paint(Graphics graphics) {
    super.paint(graphics);

    mapView.paint(graphics);
  }
}
