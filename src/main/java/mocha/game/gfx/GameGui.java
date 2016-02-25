package mocha.game.gfx;

import javafx.stage.Stage;

public class GameGui {

  public GameGui(GameView gameView) {
    Stage frame = new Stage();
    setDimensions(frame);
    frame.setResizable(false);
//    frame.setScene(gameView);
    frame.show();
  }

  private void setDimensions(Stage frame) {
    frame.setMaxWidth(600);
    frame.setMinWidth(600);
    frame.setMaxHeight(400);
    frame.setMinHeight(400);
  }
}
