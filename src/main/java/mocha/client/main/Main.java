package mocha.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.InputHandler;

@SpringBootApplication
@ComponentScan("mocha")
public class Main extends Application {
  private static String[] args;

  public static void main(String[] args) {
    Main.args = args;
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    ApplicationContext context = SpringApplication.run(Main.class, args);

    stage.setTitle("Mocha");
    Canvas canvas = new Canvas(600, 400);

    Group root = new Group();
    root.getChildren().add(canvas);

    Scene scene = new Scene(root, 600, 400);

    InputHandler input = context.getBean(InputHandler.class);
    addInputHandlers(scene, input);
    stage.setScene(scene);

    Game game = context.getBean(Game.class);
    new GameLoop(game, canvas).start();

    stage.setOnCloseRequest(event -> {Platform.exit(); System.exit(0);});

    stage.show();
  }

  private void addInputHandlers(Scene scene, InputHandler input) {
    scene.addEventHandler(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
    scene.addEventHandler(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
  }
}
