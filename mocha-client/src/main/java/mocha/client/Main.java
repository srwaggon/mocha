package mocha.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class Main extends Application {

  private static String[] args;

  public static void main(String[] args) {
    Main.args = args;
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    SpringApplication.run(Main.class, args);
  }
}
