package mocha.client.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import mocha.game.Game;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Mocha");
    Canvas canvas = new Canvas(600, 400);

    Group root = new Group();
    root.getChildren().add(canvas);

    stage.setScene(new Scene(root, 600, 400));

    final Game game = new Game();
    final long startNanoTime = System.nanoTime();
    final long[] lastTime = {startNanoTime};
    getAnimationTimer(canvas, game, startNanoTime, lastTime[0]).start();

    stage.show();
  }

  private AnimationTimer getAnimationTimer(final Canvas canvas, final Game game, final long startNanoTime, final long l) {
    return new AnimationTimer() {

      @Override
      public void handle(long now) {
        game.render(canvas.getGraphicsContext2D());
      }
    };
  }
}
