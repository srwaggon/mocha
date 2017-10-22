package mocha.game.world;

import org.springframework.stereotype.Component;

import java.util.HashMap;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.map.Map;
import mocha.game.world.map.MapFactory;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class World {

  @Inject
  private MapFactory mapFactory;

  private HashMap<Integer, Map> world = new HashMap<>();

  public void addMap(Map map) {
    world.put(map.getId(), map);
  }

  public Map getMapById(int mapId) {
    return world.get(mapId);
  }
}
