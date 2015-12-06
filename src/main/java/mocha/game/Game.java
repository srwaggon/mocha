package mocha.game;

import mocha.game.world.Map;
import mocha.game.world.World;

public class Game {

  private World world = new World();

  public Game() {
    world.addMap(new Map(1, 10, 6));
  }

  public World getWorld() {
    return world;
  }
}
