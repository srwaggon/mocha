package mocha.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mocha.game.Game;
import mocha.game.GameLoop;

@SpringBootApplication
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

    addInputHandlers(scene);
    stage.setScene(scene);

    final Game game = new Game();
    new GameLoop(game, canvas).start();

    stage.setOnCloseRequest(event -> {Platform.exit(); System.exit(0);});

    stage.show();
  }

  private void addInputHandlers(Scene scene) {
    addKeyPressedHandler(scene);
    addKeyReleasedHandler(scene);
  }

  private void addKeyPressedHandler(Scene scene) {
    scene.addEventHandler(KeyEvent.KEY_PRESSED, getKeyPressedHandler());
  }

  private EventHandler<KeyEvent> getKeyPressedHandler() {
    return event -> System.out.println("DOWN " + event.getCode());
  }

  private void addKeyReleasedHandler(Scene scene) {
    scene.addEventHandler(KeyEvent.KEY_RELEASED, getKeyReleasedHandler());
  }

  private EventHandler<KeyEvent> getKeyReleasedHandler() {
    return event -> System.out.println("UP " + event.getCode());
  }
}
