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
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mocha.game.Game;
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
    Image image = new Image("sprites.png");
    WritableImage writableImage = new WritableImage(600, 400);
    for (int y = 0; y < 400; y++) {
      for (int x = 0; x < 512; x++) {
        int argb = image.getPixelReader().getArgb(x, y);
        writableImage.getPixelWriter().setArgb(x, y, argb);
      }
    }

    canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);


    Group root = new Group();
    root.getChildren().add(canvas);

    InputHandler input = context.getBean(InputHandler.class);

    Scene scene = new Scene(root, 600, 400);
    stage.setScene(scene);
    addInputHandlers(scene, input);

    Game game = context.getBean(Game.class);
//    new GameLoop(game, canvas).start();

    stage.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });

    stage.show();
  }

  private void addInputHandlers(Scene scene, InputHandler input) {
    scene.addEventHandler(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
    scene.addEventHandler(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
  }
}
