package mocha.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import mocha.client.main.gfx.RenderLoop;
import mocha.game.GameLoop;
import mocha.game.InputHandler;

@SpringBootApplication
@ComponentScan("mocha")
@EnableAutoConfiguration
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
    Canvas canvas = context.getBean(Canvas.class);

    Image sourceImage = new Image("sprites.png");
    WritableImage destinationImage = new WritableImage(600, 400);

    copyImage(sourceImage, destinationImage);

    canvas.getGraphicsContext2D().drawImage(destinationImage, 0, 0);

    Group root = new Group();
    root.getChildren().add(canvas);

    InputHandler input = context.getBean(InputHandler.class);

    Scene scene = new Scene(root, 600, 400);
    stage.setScene(scene);
    addInputHandlers(scene, input);

    context.getBean(GameLoop.class).start();
//    context.getBean(RenderLoop.class).start();

    stage.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });

    stage.show();
  }

  private void copyImage(Image sourceImage, WritableImage destinationImage) {
    int spriteX = 0;
    int spriteY = 0;
    int canvasX = 0;
    int canvasY = 0;
    for (int x = 0; x < 16; x++) {
      for (int y = 0; y < 16; y++) {
        if (!isWithinRectangle(spriteX + x, spriteY + y, sourceImage.getWidth(), sourceImage.getHeight())) {
          continue;
        }
        if (!isWithinRectangle(canvasX + x, canvasY + y, destinationImage.getWidth(), destinationImage.getHeight())) {
          continue;
        }
        int argb = sourceImage.getPixelReader().getArgb(spriteX + x, spriteY + y);
        destinationImage.getPixelWriter().setArgb(canvasX + x, canvasY + y, argb);
      }
    }
  }

  private boolean isWithinRectangle(double x, double y, double width, double height) {
    return isInRange(x, 0, width) && isInRange(y, 0, height);
  }

  private boolean isInRange(double x, double lower, double upper) {
    return lower <= x && x < upper;
  }

  private void addInputHandlers(Scene scene, InputHandler input) {
    scene.addEventHandler(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
    scene.addEventHandler(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
  }
}
