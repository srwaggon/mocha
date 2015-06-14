package mocha.main;

import mocha.game.Game;
import mocha.game.mocha.game.gfx.GameGui;
import mocha.game.mocha.game.gfx.GameView;


public class Main {
  public static void main(String[] args) {
    new GameGui(new GameView(new Game()));
  }
}
