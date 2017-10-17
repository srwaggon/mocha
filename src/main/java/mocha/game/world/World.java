package mocha.game.world;

import org.springframework.stereotype.Component;

import java.util.HashMap;

import mocha.game.Tickable;
import mocha.game.world.map.Map;

@Component
public class World implements Tickable {

  private HashMap<Integer, Map> world = new HashMap<>();

  public void addMap(Map map) {
    world.put(map.getId(), map);
  }

  public Map getMapById(int mapId) {
    return world.get(mapId);
  }

  public void tick(long now) {
    for (Map map : world.values()) {
      map.tick(now);
    }
  }
}
