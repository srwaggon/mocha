package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.world.map.Map;
import mocha.game.world.map.MapView;

@Component
public class GameView implements Drawable {

  @Inject
  private Game game;

  private MapView mapView = new MapView();

  @PostConstruct
  public void init() {
    Map displayedMap = game.getWorld().getMapById(0);
    this.mapView.setMap(displayedMap);
  }

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    mapView.draw(mochaCanvas, 0, 0);
  }
}
