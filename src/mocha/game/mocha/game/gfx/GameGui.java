package mocha.game.mocha.game.gfx;

import javax.swing.*;

public class GameGui {

  public GameGui(GameView gameView) {
    JFrame frame = new JFrame();
    frame.setSize(600, 400);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setContentPane(gameView);
  }
}
