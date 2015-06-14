package mocha.game;

import mocha.game.world.Map;

public class Game {

  public Map getWorld() {
    return new Map(10, 6);
  }

}
