package mocha.client.main;

import mocha.game.Game;
import mocha.game.gfx.GameGui;
import mocha.game.gfx.GameView;


public class Main {
  public static void main(String[] args) {
    Game game = new Game();
    GameView gameView = new GameView(game);
    new GameGui(gameView);
  }

}
