package mocha.game;

import mocha.game.world.Map;

import java.util.HashMap;

public class Game {

  private HashMap<Integer, Map> world;

  public Game() {
    world = new HashMap<Integer, Map>();
    Map map = new Map(1, 10, 6);
    world.put(map.getId(), map);
  }

  public HashMap<Integer, Map> getWorld() {
    return world;
  }

  public void put(Map map) {
    world.put(map.getId(), map);
  }
}
