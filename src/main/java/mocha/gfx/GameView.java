package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.map.Map;
import mocha.game.world.map.MapView;

@Component
public class GameView implements Drawable {

  @Inject
  private Game game;

  private MapView mapView = new MapView();

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    this.mapView.setMap(getPlayersCurrentMap());
    mapView.draw(mochaCanvas, 0, 0);
  }

  private Map getPlayersCurrentMap() {
    int playerMapId = game.getPlayer().getMapId();
    return game.getWorld().getMapById(playerMapId);
  }
}
