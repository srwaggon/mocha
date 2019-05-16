package mocha.client.gfx;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import mocha.net.MochaConnectionProvider;

@Qualifier("menuSelection")
@Component
public class MenuSelectionScreen extends Group {

  public MenuSelectionScreen(
      WindowDimensions windowDimensions,
      MochaConnectionProvider mochaConnectionProvider
  ) {
    TextField accountTextField = new TextField("link");

    Label accountTextFieldLabel = new Label("Account Name");
    accountTextFieldLabel.setLabelFor(accountTextField);

    Button loginButton = loginButton(mochaConnectionProvider, accountTextField.textProperty());

    Button registerButton = registerAccountButton(mochaConnectionProvider, accountTextField.textProperty());

    VBox menuControls = new VBox(5,
        accountTextFieldLabel,
        accountTextField,
        loginButton,
        registerButton
    );
    menuControls.getChildren().forEach(node -> VBox.setMargin(node, new Insets(5)));

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(menuControls);
    stackPane.setAlignment(Pos.CENTER);
    stackPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("63A"), null, null)));
    stackPane.prefWidthProperty().bind(windowDimensions.getWidthProperty());
    stackPane.prefHeightProperty().bind(windowDimensions.getHeightProperty());

    this.getChildren().add(stackPane);
  }

  private Button registerAccountButton(
      MochaConnectionProvider mochaConnectionProvider,
      StringProperty accountName
  ) {
    Button registerAccountButton = new Button("Register Account");
    registerAccountButton.setOnAction(event -> mochaConnectionProvider.get().sendCreateAccountRequest(accountName.get(), ""));
    return registerAccountButton;
  }

  private Button loginButton(
      MochaConnectionProvider mochaConnectionProvider,
      StringProperty accountName
  ) {
    Button loginButton = new Button("Login");
    loginButton.isDefaultButton();
    loginButton.setOnAction(event -> mochaConnectionProvider.get().sendLoginRequest(accountName.get()));
    return loginButton;
  }
}
