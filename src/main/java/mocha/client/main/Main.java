package mocha.client.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import mocha.game.Game;
import mocha.game.gfx.GameView;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Mocha");

    Canvas canvas = new Canvas(600, 400);
    new GameView(new Game()).paint(canvas.getGraphicsContext2D());

    Group root = new Group();
    root.getChildren().add(canvas);

    stage.setScene(new Scene(root, 600, 400));
    stage.show();
  }
}
