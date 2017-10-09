package mocha.game.world;

import org.springframework.stereotype.Component;

import java.util.HashMap;

import mocha.game.world.map.Map;

@Component
public class World {

  private HashMap<Integer, Map> world = new HashMap<>();

  public void addMap(Map map) {
    world.put(map.getId(), map);
  }

  public Map getMapById(int mapId) {
    return world.get(mapId);
  }

  public void tick() {
    world.values().stream().forEach(Map::tick);
  }
}
