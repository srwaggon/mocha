package mocha.game.command;

import mocha.game.Game;

public interface Command {

  void apply(Game game);
}
