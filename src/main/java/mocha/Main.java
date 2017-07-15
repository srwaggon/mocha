package mocha;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mocha.game.GameLoop;
import mocha.game.InputHandler;
import mocha.gfx.RenderLoop;

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

    Canvas canvas = context.getBean(Canvas.class);

    Group root = new Group();
    root.getChildren().add(canvas);

    Scene scene = new Scene(root, 600, 400);
    stage.setScene(scene);
    stage.setTitle("Mocha");

    addInputHandler(context, scene);

    context.getBean(RenderLoop.class).start();
    context.getBean(GameLoop.class).start();

    stage.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });

    stage.show();
  }

  private void addInputHandler(ApplicationContext context, Scene scene) {
    InputHandler input = context.getBean(InputHandler.class);
    scene.addEventHandler(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
    scene.addEventHandler(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
  }

}
