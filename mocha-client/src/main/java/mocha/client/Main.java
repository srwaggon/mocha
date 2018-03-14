package mocha.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import mocha.client.gfx.MochaScene;
import mocha.client.gfx.RenderLoop;

@SpringBootApplication
public class Main extends Application {

  private static String[] args;

  public static void main(String[] args) {
    Main.args = args;
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    ApplicationContext context = SpringApplication.run(Main.class, args);

    stage.setTitle("Mocha Client");
    stage.setScene(context.getBean(MochaScene.class));

    context.getBean(RenderLoop.class).start();

    closeOnExit(stage);
    stage.show();
  }

  private void closeOnExit(Stage stage) {
    stage.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });
  }
}
