package mocha.game;

import mocha.game.command.Command;

public interface CommandHandler<C extends Command> {

  void handle(C c);
}
