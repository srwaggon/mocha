package mocha.game;

import mocha.game.world.Map;
import mocha.game.world.World;
import mocha.game.world.entity.Mob;

public class Game {

  private World world = new World();

  public Game() {
    addMaps();
    addEntities();
  }

  private void addEntities() {
    new Mob(0);
  }

  private void addMaps() {
    world.addMap(new Map(1, 10, 6));
  }

  public World getWorld() {
    return world;
  }
}
