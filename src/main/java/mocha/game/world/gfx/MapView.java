package mocha.game.world.gfx;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;
import mocha.game.world.Map;
import mocha.game.world.Tile;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapView implements Renderable {

  private Map map;
  private List<TileView> tileViews = new ArrayList<>();

  public MapView(Map map) {
    this.map = map;
    Tile[][] tiles = map.getTiles();
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        tileViews.add(new TileView(tiles[x][y], x, y));
      }
    }
  }

  public void paint(GraphicsContext graphics) {
    for(TileView tileView : tileViews) {
      tileView.paint(graphics);
    }
  }
}
